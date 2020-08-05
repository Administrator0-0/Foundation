package com.almoon.foundation_processor

import java.util.*
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements
import kotlin.collections.ArrayList


class ClassCreatorProxy(elements: Elements, val typeElement: TypeElement, val isActivity: Boolean) {
    private val mBindingClassName: String
    private val mPackageName: String
    private val mVariableElementHashMap = HashMap<String, ArrayList<ExecutableElement>>()
    private val TAB = "    "


    val proxyClassFullClassName: String
        get() = "$mPackageName.$mBindingClassName"

    init {
        val packageElement = elements.getPackageOf(typeElement)
        val packageName = packageElement.qualifiedName.toString()
        val className = typeElement.simpleName.toString()
        mPackageName = packageName
        mBindingClassName = className + "_FoundationBinding"
    }

    fun putElement(id: String, executableElement: ExecutableElement) {
        if (mVariableElementHashMap[id] == null) {
            mVariableElementHashMap[id] = ArrayList()
        }
        mVariableElementHashMap[id]!!.add(executableElement)
    }

    fun generateJavaCode(): String {
        val builder = StringBuilder()
        builder.append("package ").append(mPackageName).append(";\n\n")
        builder.append("import com.almoon.foundation_lib.*;\n\n")
        builder.append("public class ").append(mBindingClassName).append(" {\n")
        builder.append(TAB)
        generateMethods(builder)
        builder.append("}\n")
        return builder.toString()
    }

    private fun generateMethods(builder: StringBuilder) {
        builder.append("public void bind(").append(typeElement.qualifiedName)
            .append(" host) {\n")
        for (id in mVariableElementHashMap.keys) {
            val elements = mVariableElementHashMap[id]
            if (elements == null) {
                mVariableElementHashMap[id] = ArrayList()
            }
            for (element in elements!!) {
                val name = element.simpleName.toString()
                if (isActivity) {
                    builder.append(TAB).append(TAB)
                    builder.append("(($typeElement)host).").append(id).append(".observe(")
                        .append("($typeElement)host, data -> {")
                        .append("\n")
                        .append(TAB).append(TAB).append(TAB).append("(($typeElement)host).")
                        .append(name).append("();\n")
                        .append(TAB).append(TAB).append("});\n")
                } else {
                    builder.append(TAB).append(TAB)
                    builder.append("(($typeElement)host).").append(id).append(".observe(")
                        .append("($typeElement)host.getViewLifecycleOwner(), data -> {")
                        .append("\n")
                        .append(TAB).append(TAB).append(TAB).append("(($typeElement)host).")
                        .append(name).append("();\n")
                        .append(TAB).append(TAB).append("});\n")
                }
            }
        }
        builder.append(TAB).append("}\n")
    }

}