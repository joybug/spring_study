package hello.hellospring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
public class TimeTraceAop {

    //@Around("execution(* hello.hellospring..*(..))") -> 이렇게 되면 SpringConfig.class 에서 TimeTraceAop 빈등록을 위해 timeTraceAop() 호출시 순환참조 발생
    @Around("execution(* hello.hellospring..*(..)) && !target(hello.hellospring.SpringConfig)") //aop 대상에서 SpringConfig.class를 제외함
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        //System.out.println("START: "+joinPoint.toString());

        try{
            return joinPoint.proceed();
        }finally {
            long finish = System.currentTimeMillis();
            //long timeMs = finish - start;

            Signature sig = joinPoint.getSignature();
            System.out.printf("%s.%s(%s) 실행 시간 : %d ms\n", joinPoint.getTarget().getClass().
                    getSimpleName(), sig.getName(), Arrays.toString(joinPoint.getArgs()), (finish - start));
        }
    }
}
