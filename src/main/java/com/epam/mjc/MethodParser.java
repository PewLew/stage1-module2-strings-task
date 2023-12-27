package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        StringTokenizer tok = new StringTokenizer(signatureString, "\\(\\)");
        List<String> properties = new ArrayList<>();
        String[] forName;
        List<MethodSignature.Argument> forArgs = new ArrayList<>();
        while (tok.hasMoreTokens()){
            properties.add(tok.nextToken());
        }
        if(properties.size() == 1){
            forName = methodName(properties.get(0));
        }
        else{
            forName = methodName(properties.get(0));
            forArgs = args(properties.get(1));
        }

        String name;
        String accessModifier = null;
        String returnType;

        if(forName.length == 3){
            accessModifier = forName[0];
            returnType = forName[1];
            name = forName[2];
        }
        else{
            returnType = forName[0];
            name = forName[1];
        }
        MethodSignature methodSignature = new MethodSignature(name, forArgs);
        methodSignature.setMethodName(name);
        methodSignature.setAccessModifier(accessModifier);
        methodSignature.setReturnType(returnType);
        return methodSignature;
    }

    public static String[] methodName(String forName){
        return forName.split(" ");
    }

    public static List<MethodSignature.Argument> args(String arg){
        StringTokenizer tok = new StringTokenizer(arg, "\\ \\,");
        List<String>  fromToken = new ArrayList<>();
        List<MethodSignature.Argument> args = new ArrayList<MethodSignature.Argument>();
        while (tok.hasMoreTokens()){
            fromToken.add(tok.nextToken());
        }

        for(int i = 0; i<fromToken.size(); i++){
            if(i%2 == 0){
                String type, name;
                type = fromToken.get(i);
                name = fromToken.get(i+1);
                MethodSignature.Argument a = new MethodSignature.Argument(type, name);
                args.add(a);
            }
        }

        return args;
    }


}
