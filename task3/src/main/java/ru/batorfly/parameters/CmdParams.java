package ru.batorfly.parameters;

public record CmdParams (
        String inputFile,
        boolean consoleMode,
        boolean fileMode) { }
