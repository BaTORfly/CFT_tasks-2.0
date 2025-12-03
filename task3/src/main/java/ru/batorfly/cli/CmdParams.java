package ru.batorfly.cli;

public record CmdParams (
        String inputFile,
        boolean consoleMode,
        boolean fileMode) { }
