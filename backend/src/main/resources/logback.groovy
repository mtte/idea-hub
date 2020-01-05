import ch.qos.logback.classic.boolex.GEventEvaluator
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.filter.EvaluatorFilter

import static ch.qos.logback.core.spi.FilterReply.DENY
import static ch.qos.logback.core.spi.FilterReply.NEUTRAL

String encoderPattern = "%d{yyyy-MM-dd' 'HH:mm:ss.SSS} %-5level [%thread] %logger{36} : %msg%n"

appender("STDOUT", ConsoleAppender) {
    filter(EvaluatorFilter) {
        evaluator(GEventEvaluator) {
            expression = "e.level.toInt() <= INFO.toInt()"
        }
        onMismatch = DENY
        onMatch = NEUTRAL
    }
    encoder(PatternLayoutEncoder) {
        pattern = encoderPattern
    }
}

appender("STDERR", ConsoleAppender) {
    target = "System.err"
    filter(ThresholdFilter) {
        level = WARN
    }
    encoder(PatternLayoutEncoder) {
        pattern = encoderPattern
    }
}

appender("FILE", RollingFileAppender) {
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "logs/%d{yyyy-MM-dd}.log"
        maxHistory = 7
    }
    encoder(PatternLayoutEncoder) {
        pattern = encoderPattern
    }
}

root(TRACE, ["FILE", "STDOUT", "STDERR"])
