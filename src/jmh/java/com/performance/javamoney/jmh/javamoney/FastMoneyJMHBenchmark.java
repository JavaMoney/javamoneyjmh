package com.performance.javamoney.jmh.javamoney;


import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.math.BigDecimal;

//@BenchmarkMode({Mode.AverageTime})

//@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 3)
@Measurement(iterations = 8)
//@Threads(1)
public class FastMoneyJMHBenchmark {

    @State(Scope.Benchmark)
    public static class AdditionalState {
        public FastMoney fmoney1;
        public FastMoney fmoney2;
        public Long longNumber1;
        public Long longNumber2;
        public BigDecimal bdNumber1;
        public BigDecimal bdNumber2;
        public Money money1;
        public Money money2;


        @Setup(Level.Iteration)
        public void doInitializing() {
            longNumber1 = 9877L;
            longNumber2 = 1282L;
            bdNumber1 = new BigDecimal(longNumber1);
            bdNumber2 = new BigDecimal(longNumber2);
            fmoney1 = FastMoney.of(longNumber1, "EUR");
            fmoney2 = FastMoney.of(longNumber2, "EUR");
            money1 = Money.of(longNumber1, "EUR");
            money2 = Money.of(longNumber2, "EUR");
        }
    }


    @Benchmark
    public void multiplyLong(AdditionalState state, Blackhole bh) {
        bh.consume(state.longNumber1 * state.longNumber2);
    }

    @Benchmark
    public void multiplyFastMoneyGetNumber(AdditionalState state, Blackhole bh) {
        bh.consume(state.fmoney1.multiply(state.fmoney2.getNumber()));
    }

    @Benchmark
    public void multiplyFastMoneyDirect(AdditionalState state, Blackhole bh) {
        bh.consume(state.fmoney1.multiply(state.longNumber2));
    }

    @Benchmark
    public void multiplyMoneyGetNumber(AdditionalState state, Blackhole bh) {
        bh.consume(state.money1.multiply(state.money2.getNumber()));
    }

    @Benchmark
    public void multiplyMoneyDirect(AdditionalState state, Blackhole bh) {
        bh.consume(state.money1.multiply(state.longNumber2));
    }

    @Benchmark
    public void multiplyBigDecimalDirect(AdditionalState state, Blackhole bh) {
        bh.consume(state.bdNumber1.multiply(state.bdNumber2));
    }

    @Benchmark
    public void javaMoneyNotReliableTest(AdditionalState state, Blackhole bh) {

        Money money1 = state.money1.add(Money.of(1234567.3444, "EUR"));
        money1 = money1.subtract(Money.of(232323, "EUR"));
        money1 = money1.multiply(3.4);
        money1 = money1.divide(5.456);
        bh.consume(money1);
    }

    @Benchmark
    public void javaCombinationNotReliableTest(AdditionalState state, Blackhole bh) {
        Money fmoney1 = state.money1.add(FastMoney.of(1234567.3444, "EUR"));
        fmoney1 = fmoney1.subtract(FastMoney.of(232323, "EUR"));
        fmoney1 = fmoney1.multiply(3.4);
        fmoney1 = fmoney1.divide(5.456);
        bh.consume(fmoney1);
    }

    @Benchmark
    public void javaFastMoneyNotReliableTest(AdditionalState state, Blackhole bh) {
        FastMoney fmoney1 = state.fmoney1.add(FastMoney.of(1234567.3444, "EUR"));
        fmoney1 = fmoney1.subtract(FastMoney.of(232323, "EUR"));
        fmoney1 = fmoney1.multiply(3.4);
        fmoney1 = fmoney1.divide(5.456);
        bh.consume(fmoney1);
    }

    @Benchmark
    public void sumFastMoney(AdditionalState state, Blackhole bh) {
        bh.consume(state.fmoney1.add(state.fmoney2));
    }

    @Benchmark
    public void sumMoney(AdditionalState state, Blackhole bh) {
        bh.consume(state.money1.add(state.money1));
    }

    @Benchmark
    public void sumLong(AdditionalState state, Blackhole bh) {
        bh.consume(state.longNumber1 + state.longNumber1);
    }

    @Benchmark
    public void sumBigDecimal(AdditionalState state, Blackhole bh) {
        bh.consume(state.bdNumber1.add(state.bdNumber2));
    }

    @Benchmark
    public void subFastMoney(AdditionalState state, Blackhole bh) {
        bh.consume(state.fmoney1.subtract(state.fmoney2));
    }

    @Benchmark
    public void subMoney(AdditionalState state, Blackhole bh) {
        bh.consume(state.money1.subtract(state.money1));
    }

    @Benchmark
    public void subLong(AdditionalState state, Blackhole bh) {
        bh.consume(state.longNumber1 - state.longNumber2);
    }

    @Benchmark
    public void subBigDecimal(AdditionalState state, Blackhole bh) {
        bh.consume(state.bdNumber1.subtract(state.bdNumber2));
    }

}

