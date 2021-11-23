package com.enjoy.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * 给代码增加try catch代码块
 */
public class TryCatchBlockVisitor extends ClassVisitor {

    public TryCatchBlockVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        return new TryCatchAdapterVisitor(mv, access, name, desc);
    }

    public class TryCatchAdapterVisitor extends AdviceAdapter {

        /**
         * Creates a new {@link AdviceAdapter}.
         *
         * @param mv     the method visitor to which this adapter delegates calls.
         * @param access the method's access flags (see {@link Opcodes}).
         * @param name   the method's name.
         * @param desc   the method's descriptor (see {@link Type Type}).
         */
        protected TryCatchAdapterVisitor(MethodVisitor mv, int access, String name, String desc) {
            super(Opcodes.ASM5, mv, access, name, desc);
        }


        private Label labelStart = new Label();
        private Label labelEnd = new Label();
        private Label labelTarget = new Label();

        @Override
        protected void onMethodEnter() {
            // 定义开始位置
            mv.visitLabel(labelStart);
            // 开始 try...catch 块
            mv.visitTryCatchBlock(labelStart, labelEnd, labelTarget, "java/lang/Exception");
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            // 定义正常代码结束的位置
            mv.visitLabel(labelEnd);
            // 定义 catch 块开始的位置
            mv.visitLabel(labelTarget);
            int local1 = newLocal(Type.getType("Ljava/lang/Exception;"));
            mv.visitVarInsn(Opcodes.ASTORE, local1);
            mv.visitVarInsn(Opcodes.ALOAD, local1);
            // 输出 ex.printStackTrace
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Exception", "printStackTrace", "()V", false);
            //判断方法的返回类型
//            mv.visitInsn(getReturnCode(descriptor = descriptor))
            super.visitMaxs(maxStack, maxLocals);
        }

//        /**
//         * 获取对应的返回值
//         */
//        private fun getReturnCode(descriptor: String?): Int {
//            return when (descriptor!!.subSequence(descriptor.indexOf(")") + 1, descriptor.length)) {
//                "V" -> Opcodes.RETURN
//                "I", "Z", "B", "C", "S" -> {
//                    mv.visitInsn(Opcodes.ICONST_0)
//                    Opcodes.IRETURN
//                }
//                "D" -> {
//                    mv.visitInsn(Opcodes.DCONST_0)
//                    Opcodes.DRETURN
//                }
//                "J" -> {
//                    mv.visitInsn(Opcodes.LCONST_0)
//                    Opcodes.LRETURN
//                }
//                "F" -> {
//                    mv.visitInsn(Opcodes.FCONST_0)
//                    Opcodes.FRETURN
//                }
//            else -> {
//                    mv.visitInsn(Opcodes.ACONST_NULL)
//                    Opcodes.ARETURN
//                }
//            }
//        }
    }
}
