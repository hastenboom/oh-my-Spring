package com.student.ohmyspring.core.aop.aspect.advisor;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Student
 */
@Data
public class PointCut {

    final String expression;

    private String packageAndClassName;
    private String returnType;
    private String[] argsTypeList = new String[]{};
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

    // execution(* com.example.service..*(String,String))
    public void parseExpression(String expression) {

        Matcher matcher = supportedExpressionType.matcher(expression);
        String details = null;
        if (matcher.find()) {
            //1. details = * com.example.service..*(String,String)
            details = matcher.group(1);

            String[] split = Pattern.compile("\\s")
                    .splitAsStream(details)
                    .filter(str -> !str.isEmpty())
                    .toArray(String[]::new);
            //2 split[0] = *
            returnType = split[0];

            //3 split[1] = com.example.service..*(String,String)
            String packageClassMethodArgsType = split[1];

            int leftBracketIndex = packageClassMethodArgsType.indexOf('(');
            int rightBracketIndex = packageClassMethodArgsType.lastIndexOf(')');

            //3.1 unhandledArgTypeString = String,String
            String unhandledArgTypeString = packageClassMethodArgsType.substring(leftBracketIndex + 1,
                    rightBracketIndex);
            if (unhandledArgTypeString.contains("..")) {
                argsTypeList[0] = unhandledArgTypeString;
            }
            else {
                argsTypeList = Pattern.compile(",")
                        .splitAsStream(unhandledArgTypeString)
                        .filter(s -> !s.isEmpty())
                        .toArray(String[]::new);
            }

            //3.2
            // com.example.service..*(..)，service“包下所有”带“任意参数”的“所有方法”
            //                      ⬆
            //                      i is here, represents the method name
            // com.example.service..login(String, String)，service包下所有带login(String, String)的方法
            //                      ⬆
            //                      i is here, represents the method name
            int i = packageClassMethodArgsType.lastIndexOf('.', leftBracketIndex - 1);
            if (packageClassMethodArgsType.substring(leftBracketIndex - 1, leftBracketIndex) == "*") {
                methodName = "*";
            }
            else {
                methodName = packageClassMethodArgsType.substring(i + 1, leftBracketIndex);
            }

            //3.3 packageAndClassName = com.example.service.UserService
            String unhandledPackageClassString = packageClassMethodArgsType.substring(0, i);
            if (unhandledPackageClassString.endsWith(".")) {
                className = ".";
            }
            else {
                className = unhandledPackageClassString.
                        substring(unhandledPackageClassString.lastIndexOf(".") + 1);
            }
            packageName =
                    unhandledPackageClassString.substring(0, unhandledPackageClassString.lastIndexOf("."));


        }


    }


    public boolean matches(
            @NotNull Method method)
    {

        String methodReturnType = method.getReturnType().getSimpleName();
        String[] methodArgsTypeList =
                Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).toArray(String[]::new);
        String methodMethodName = method.getName();

        String packageAndClassName = method.getDeclaringClass().getName();
        int index = packageAndClassName.lastIndexOf('.');
        String packageName = packageAndClassName.substring(0, index);
        String className = packageAndClassName.substring(index + 1);

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

    public boolean matchArgTypeList(String[] methodArgsTypeList) {
        if (this.argsTypeList.length == 1 && this.argsTypeList[0].equals("..")) {
            return true;
        }

        if (this.argsTypeList.length != methodArgsTypeList.length) {
            return false;
        }

        for (int i = 0; i < this.argsTypeList.length; i++) {
            if (!this.argsTypeList[i].equals(methodArgsTypeList[i])) {
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
        if (this.className.equals(".")) {
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

}
