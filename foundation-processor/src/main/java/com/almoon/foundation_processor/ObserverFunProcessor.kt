package com.almoon.foundation_processor

import java.io.IOException
import java.util.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic
import com.almoon.foundation_annotation.ObserveFun
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror

class ObserverFunProcessor : AbstractProcessor() {
    private var mMessager: Messager? = null
    private var mElements: Elements? = null
    private val mPoxyMap: MutableMap<String, ClassCreatorProxy> = HashMap()
    private val TYPE_ACTIVITY = "android.app.Activity"
    private val TYPE_FRAGMENT = "androidx.fragment.app.Fragment"

    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        mMessager = processingEnvironment.messager
        mElements = processingEnvironment.elementUtils
    }

    override fun getSupportedAnnotationTypes(): Set<String>? {
        val supportTypes: HashSet<String> = LinkedHashSet()
        supportTypes.add(ObserveFun::class.java.canonicalName)
        return supportTypes
    }

    override fun getSupportedSourceVersion(): SourceVersion? {
        return SourceVersion.latestSupported()
    }

    override fun process(set: Set<TypeElement?>?, roundEnvironment: RoundEnvironment): Boolean {
        mMessager!!.printMessage(Diagnostic.Kind.NOTE, "processing....")
        mPoxyMap.clear()
        val elements: Set<Element> = roundEnvironment.getElementsAnnotatedWith(ObserveFun::class.java)
        for (element in elements) {
            val executableElement = element as ExecutableElement
            val classElement = executableElement.enclosingElement as TypeElement
            val fullClassName = classElement.qualifiedName.toString()
            var proxy = mPoxyMap[fullClassName]
            if (proxy == null) {
                when {
                    isSubtypeOfType(classElement.asType(), TYPE_ACTIVITY) -> {
                        proxy = ClassCreatorProxy(mElements!!, classElement, true)
                        mPoxyMap[fullClassName] = proxy
                    }
                    isSubtypeOfType(classElement.asType(), TYPE_FRAGMENT) -> {
                        proxy = ClassCreatorProxy(mElements!!, classElement, false)
                        mPoxyMap[fullClassName] = proxy
                    }
                    else -> {
                        throw Throwable("ObserveFun only can be used in activity or fragment!")
                    }
                }
            }
            val observeFun: ObserveFun = executableElement.getAnnotation<ObserveFun>(ObserveFun::class.java)
            val id: String = observeFun.value
            proxy.putElement(id, executableElement)
        }
        for (key in mPoxyMap.keys) {
            val proxy = mPoxyMap[key]
            if (proxy != null) {
                mMessager!!.printMessage(Diagnostic.Kind.NOTE, "create " + proxy.proxyClassFullClassName)
                try {
                    val fileObject = processingEnv.filer
                        .createSourceFile(proxy.proxyClassFullClassName, proxy.typeElement)
                    val writer = fileObject.openWriter()
                    writer.write(proxy.generateJavaCode())
                    writer.flush()
                    writer.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        mMessager!!.printMessage(Diagnostic.Kind.NOTE, "process finish")
        return true
    }

    fun isSubtypeOfType(typeMirror: TypeMirror, otherType: String): Boolean {
        if (isTypeEqual(typeMirror, otherType)) {
            return true
        }
        if (typeMirror.kind != TypeKind.DECLARED) {
            return false
        }
        val declaredType = typeMirror as DeclaredType
        val typeArguments = declaredType.typeArguments
        if (typeArguments.size > 0) {
            val typeString = java.lang.StringBuilder(declaredType.asElement().toString())
            typeString.append('<')
            for (i in typeArguments.indices) {
                if (i > 0) {
                    typeString.append(',')
                }
                typeString.append('?')
            }
            typeString.append('>')
            if (typeString.toString() == otherType) {
                return true
            }
        }
        val element: Element = declaredType.asElement() as? TypeElement ?: return false
        val typeElement = element as TypeElement
        val superType = typeElement.superclass
        if (isSubtypeOfType(superType, otherType)) {
            return true
        }
        for (interfaceType in typeElement.interfaces) {
            if (isSubtypeOfType(interfaceType, otherType)) {
                return true
            }
        }
        return false
    }

    private fun isTypeEqual(typeMirror: TypeMirror, otherType: String): Boolean {
        return otherType == typeMirror.toString()
    }
}