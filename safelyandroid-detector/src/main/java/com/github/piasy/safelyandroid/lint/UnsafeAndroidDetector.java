package com.github.piasy.safelyandroid.lint;

import com.android.tools.lint.client.api.JavaParser;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import java.util.Arrays;
import java.util.List;
import lombok.ast.AstVisitor;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.MethodInvocation;
import lombok.ast.Node;
import lombok.ast.Return;
import lombok.ast.This;

/**
 * Created by Piasy{github.com/Piasy} on 16/4/16.
 */
public class UnsafeAndroidDetector extends Detector implements Detector.JavaScanner {
    public static final String DISMISS = "dismiss";
    public static final String COMMIT = "commit";
    public static final String DIALOG_FRAGMENT = "android.app.DialogFragment";
    public static final String V4_DIALOG_FRAGMENT = "android.support.v4.app.DialogFragment";

    public static final String DESCRIPTION_UNSAFE_DISMISS = "Dismiss dialog fragment unsafely";
    public static final String DESCRIPTION_UNSAFE_COMMIT = "Commit fragment transaction unsafely";

    public static final Issue ISSUE_UNSAFE_DISMISS =
            Issue.create("UnsafeDismiss", DESCRIPTION_UNSAFE_DISMISS,
                    "You should use safely way to dismiss dialog fragment to avoid activity state" +
                            " loss.", Category.CORRECTNESS, 8, Severity.ERROR,
                    new Implementation(UnsafeAndroidDetector.class, Scope.JAVA_FILE_SCOPE));
    public static final Issue ISSUE_UNSAFE_COMMIT =
            Issue.create("UnsafeCommit", DESCRIPTION_UNSAFE_COMMIT,
                    "You should use safely way to commit fragment transaction to avoid activity " +
                            "state loss.", Category.CORRECTNESS, 8, Severity.ERROR,
                    new Implementation(UnsafeAndroidDetector.class, Scope.JAVA_FILE_SCOPE));

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList(DISMISS, COMMIT);
    }

    @Override
    public void visitMethod(JavaContext context, AstVisitor visitor, MethodInvocation node) {
        String name = node.astName().astValue();
        if (DISMISS.equals(name)) {
            checkUnsafeDismiss(context, node);
        } else {
            checkUnsafeCommit(context, node);
        }
    }

    private void checkUnsafeCommit(JavaContext context, MethodInvocation node) {
        /*System.out.println(context.getLocation(node).getFile().getAbsolutePath() + ":" +
                context.getLocation(node).getStart().getLine() + ", " + node);

        System.out.println(node.rawOperand());
        System.out.println(node.rawOperand().getClass());
        for (Node child : node.rawOperand().getChildren()) {
            System.out.println("child: " + child);
        }
        System.out.println(node.rawOperand().getGeneratedBy());
        System.out.println(node.rawOperand().getNativeNode());
        System.out.println(node.rawOperand().getNativeNode().getClass());
        System.out.println(context.resolve(node.rawOperand()));
        System.out.println(context.getType(node.rawOperand()));
        System.out.println(JavaContext.getMethodName(node.rawOperand()));

        // TODO: 16/4/17
         can't get the return type of node.rawOperand(), otherwise we can check whether
         it's FragmentTransaction. after the ast is built, we should could know the return type
         of a method call.

        do {
            if (node.astOperand() == null) {
                break;
            }
            Object resolvedNode = context.resolve(node.astOperand());
            System.out.println(resolvedNode);
            if (!(resolvedNode instanceof JavaParser.ResolvedVariable)) {
                break;
            }
            JavaParser.ResolvedVariable variable = (JavaParser.ResolvedVariable) resolvedNode;
            if (isDialogFragment(variable.getType().getTypeClass())) {
                context.report(ISSUE_UNSAFE_DISMISS, node, context.getLocation(node),
                        DESCRIPTION_UNSAFE_DISMISS);
                return;
            }
        } while (false);

        Object resolvedNode = context.resolve(node);
        System.out.println(resolvedNode);
        if (!(resolvedNode instanceof JavaParser.ResolvedMethod)) {
            return;
        }
        JavaParser.ResolvedMethod resolvedMethod = (JavaParser.ResolvedMethod) resolvedNode;
        if (isDialogFragment(resolvedMethod.getContainingClass())) {
            context.report(ISSUE_UNSAFE_DISMISS, node, context.getLocation(node),
                    DESCRIPTION_UNSAFE_DISMISS);
        }*/
    }


    private static class ReceiverFinder extends ForwardingAstVisitor {
        private final MethodInvocation mTarget;
        private boolean mFound;
        private boolean mSeenTarget;

        private ReceiverFinder(MethodInvocation target) {
            this.mTarget = target;
        }

        @Override
        public boolean visitMethodInvocation(MethodInvocation node) {
            System.out.println("ReceiverFinder::visitMethodInvocation " + node);
            if(node == this.mTarget) {
                this.mSeenTarget = true;
            } else if((this.mSeenTarget || node.astOperand() == this.mTarget) && "show".equals(node.astName().astValue())) {
                this.mFound = true;
            }

            return true;
        }

        @Override
        public boolean visitReturn(Return node) {
            if(node.astValue() == this.mTarget) {
                this.mFound = true;
            }

            return super.visitReturn(node);
        }
    }

    private void checkUnsafeDismiss(JavaContext context, MethodInvocation node) {
        if (node.rawOperand() == null && isInsideDialogFragment(context, node)) {
            context.report(ISSUE_UNSAFE_DISMISS, node, context.getLocation(node),
                    DESCRIPTION_UNSAFE_DISMISS);
            return;
        }

        do {
            if (node.astOperand() == null) {
                break;
            }
            if (node.astOperand() instanceof This) {
                This thiz = (This) node.astOperand();
                Object resolvedNode = context.resolve(thiz.astQualifier());
                if (resolvedNode instanceof JavaParser.ResolvedClass &&
                        isDialogFragment((JavaParser.ResolvedClass) resolvedNode)) {
                    context.report(ISSUE_UNSAFE_DISMISS, node, context.getLocation(node),
                            DESCRIPTION_UNSAFE_DISMISS);
                    return;
                }
            }
            Object resolvedNode = context.resolve(node.astOperand());
            if (!(resolvedNode instanceof JavaParser.ResolvedVariable)) {
                break;
            }
            JavaParser.ResolvedVariable variable = (JavaParser.ResolvedVariable) resolvedNode;
            if (isDialogFragment(variable.getType().getTypeClass())) {
                context.report(ISSUE_UNSAFE_DISMISS, node, context.getLocation(node),
                        DESCRIPTION_UNSAFE_DISMISS);
                return;
            }
        } while (false);

        Object resolvedNode = context.resolve(node);
        if (!(resolvedNode instanceof JavaParser.ResolvedMethod)) {
            return;
        }
        JavaParser.ResolvedMethod resolvedMethod = (JavaParser.ResolvedMethod) resolvedNode;
        if (isDialogFragment(resolvedMethod.getContainingClass())) {
            context.report(ISSUE_UNSAFE_DISMISS, node, context.getLocation(node),
                    DESCRIPTION_UNSAFE_DISMISS);
        }
    }

    private boolean isInsideDialogFragment(JavaContext context, MethodInvocation node) {
        Node parent = node.getParent();
        while (parent != null) {
            Object resolvedNode = context.resolve(parent);
            if (resolvedNode instanceof JavaParser.ResolvedMethod) {
                JavaParser.ResolvedMethod method = (JavaParser.ResolvedMethod) resolvedNode;
                if (isDialogFragment(method.getContainingClass())) {
                    return true;
                }
            }
            parent = parent.getParent();
        }
        return false;
    }

    private boolean isDialogFragment(JavaParser.ResolvedClass clazz) {
        JavaParser.ResolvedClass superClazz = clazz;
        while (superClazz != null) {
            String name = superClazz.getName();
            if (DIALOG_FRAGMENT.equals(name) || V4_DIALOG_FRAGMENT.equals(name)) {
                return true;
            }
            superClazz = superClazz.getSuperClass();
        }
        return false;
    }
}
