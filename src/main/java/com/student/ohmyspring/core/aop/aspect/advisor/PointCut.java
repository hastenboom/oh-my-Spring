package com.student.ohmyspring.core.aop.aspect.advisor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Student
 */
@Data
@Slf4j
public class PointCut {

    final String expression;

    private String packageAndClassName;
    private String returnType;
    private List<String> argsTypeList = new ArrayList<>();
    private String methodName;
    private String className;
    private String packageName;

    private Pattern supportedExpressionType;

    public PointCut(String expression) {
        this.expression = expression;
        this.supportedExpressionType =
                Pattern.compile("^execution\\((.*)\\)");

        //initialize the rest of the fields
        parseExpression(expression);
    }

    /**
     * execution(* com.example.service.*.*(..))
     * <ul>
     *     <li>returnType = * </li>
     *     <li>packageName = com.example.service</li>
     *      <li>className = *</li>
     *      <li>methodName = *</li>
     *       <li>argsTypeList = [..]</li>
     * </ul>
     * A fairly simple implementation, it doesn't support complicated or inner-form expressions;
     *
     * @param expression
     */
    public void parseExpression(String expression) {

        Matcher matcher = supportedExpressionType.matcher(expression);
        String details = null;
        try {
            if (matcher.find()) {
                //1. details = * com.example.service.*.*(..)
                details = matcher.group(1);

                String[] split = details.trim().split("\\s+");
                //2 split[0] = *
                returnType = split[0];

                //3 split[1] = com.example.service.*.*(..)
                String packageClassMethodArgsType = split[1];

                int leftBracketIndex = packageClassMethodArgsType.indexOf('(');
                int rightBracketIndex = packageClassMethodArgsType.indexOf(')');

                //3.1 unhandledArgTypeString = String,String
                String unhandledArgTypeString = packageClassMethodArgsType.substring(leftBracketIndex + 1,
                        rightBracketIndex);

                //TODO I haven't considered (String, ..)
                if (unhandledArgTypeString.contains("..")) {
                    argsTypeList.addLast(unhandledArgTypeString);
                }
                else {
                    argsTypeList = Pattern.compile(",")
                            .splitAsStream(unhandledArgTypeString)
                            .filter(s -> !s.isEmpty())
                            .toList();
                }

                //3.2

                //packageClassMethodArgsType = com.example.service.*.*(..)
                int i = packageClassMethodArgsType.lastIndexOf('.', leftBracketIndex - 1);

                if (log.isDebugEnabled()) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < i; j++) {
                        if (j == 0) continue;
                        sb.append(" ");
                    }
                    sb.append("↑, i is here");
                    System.out.println(packageClassMethodArgsType + "\n" + sb);

                }

                if (packageClassMethodArgsType.charAt(leftBracketIndex - 1) == '*') {
                    methodName = "*";
                }
                else {
                    methodName = packageClassMethodArgsType.substring(i + 1, leftBracketIndex);
                }

                //3.3 packageAndClassName = com.example.service.UserService
                String unhandledPackageClassString = packageClassMethodArgsType.substring(0, i);
                if (unhandledPackageClassString.endsWith("*")) {
                    className = "*";
                }
                else {
                    className = unhandledPackageClassString.
                            substring(unhandledPackageClassString.lastIndexOf(".") + 1);
                }
                //TODO: simple implementation
                packageName =
                        unhandledPackageClassString.substring(0, unhandledPackageClassString.lastIndexOf("."));
            }
        }
        catch (Exception e) {
            log.error("Paring failed, check your pointcut expression.");
            throw new RuntimeException(e);
        }
    }


    public boolean matches(
            @NotNull Method method)
    {

        String methodReturnType = method.getReturnType().getSimpleName();

        List<String> methodArgsTypeList =
                Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).toList();
        String methodMethodName = method.getName();
        String packageAndClassName = method.getDeclaringClass().getName();
        int index = packageAndClassName.lastIndexOf('.');
        String packageName = packageAndClassName.substring(0, index);
        String className = packageAndClassName.substring(index + 1);

        log.debug("methodReturnType: {}", methodReturnType);
        log.debug("packageName: {}", packageName);
        log.debug("className: {}", className);
        log.debug("methodMethodName: {}", methodMethodName);
        log.debug("methodArgsTypeList: {}", methodArgsTypeList);

        return matchReturnType(methodReturnType) &&
                matchArgTypeList(methodArgsTypeList) &&
                matchMethodName(methodMethodName) &&
                matchClassName(className) &&
                matchPackageName(packageName);
    }

    public boolean matchReturnType(String methodReturnType) {
        if (this.returnType.equals("*")) {
            return true;
        }
        return this.returnType.equals(methodReturnType);
    }

    public boolean matchArgTypeList(List<String> methodArgsTypeList) {

        if (this.argsTypeList.size() == 1 && this.argsTypeList.getFirst().equals("..")) {
            return true;
        }

        if (this.argsTypeList.size() != methodArgsTypeList.size()) {
            return false;
        }

        for (int i = 0; i < this.argsTypeList.size(); i++) {
            if (!this.argsTypeList.get(i).equals(methodArgsTypeList.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean matchMethodName(String methodMethodName) {
        if (this.methodName.equals("*")) {
            return true;
        }
        return this.methodName.equals(methodMethodName);
    }

    public boolean matchClassName(String methodClassName) {
        if (this.className.equals("*")) {
            return true;
        }
        return this.className.equals(methodClassName);
    }

    public boolean matchPackageName(String methodPackageName) {
        if (!this.packageName.equals(methodPackageName) && this.className.equals(".")) {
            return true;
        }
        return this.packageName.equals(methodPackageName);
    }

    @Override
    public String toString() {
/*
        final String expression;

        private String packageAndClassName;
        private String returnType;
        private List<String> argsTypeList = new ArrayList<>();
        private String methodName;
        private String className;
        private String packageName;
*/
        return "expression:  " + expression +
                "\n\nreturnType:  " + returnType +
                "\npackageName:  " + packageName +
                "\nclassName:  " + className +
                "\nmethodName:  " + methodName +
                "\nargsTypeList:  " + argsTypeList + "\n\n";

    }
}
