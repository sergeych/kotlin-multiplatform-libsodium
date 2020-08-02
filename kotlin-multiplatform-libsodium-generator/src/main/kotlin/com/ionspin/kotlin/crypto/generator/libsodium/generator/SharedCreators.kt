package com.ionspin.kotlin.crypto.generator.libsodium.generator

import com.ionspin.kotlin.crypto.generator.libsodium.definitions.ClassDefinition
import com.ionspin.kotlin.crypto.generator.libsodium.definitions.FunctionDefinition
import com.ionspin.kotlin.crypto.generator.libsodium.definitions.InnerClassDefinition
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec

/**
 * Created by Ugljesa Jovanovic
 * ugljesa.jovanovic@ionspin.com
 * on 31-Jul-2020
 */
fun createClass(
    classDefinition: ClassDefinition,
    multiplatformModifier: MultiplatformModifier,
    methodCreator: (FunctionDefinition) -> FunSpec
): TypeSpec.Builder {
    val commonClassBuilder = TypeSpec.classBuilder(classDefinition.name)
    commonClassBuilder.modifiers += multiplatformModifier.modifierList
    for (methodDefinition in classDefinition.methods) {
        commonClassBuilder.addFunction(methodCreator(methodDefinition))
    }
    return commonClassBuilder
}


