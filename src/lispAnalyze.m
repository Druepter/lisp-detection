function isLisp = lispAnalyze(audio, normal, lisp, rest)
    disp("analyzing...")

    % use standard deviation to detect speech
    % the julia script used a larger audio file as reference
    % and compared segment means to the full audio file's to
    % determine silence but this is easier on memory
    % an alternative to this would be a noise gate or a
    % reference mean found during calibration
    if std(audio) > 0.02
        isLisp = 0;
        return
    end

    audioFft = fft(audio);

    % return 1 if this segment is a lisp and -1 if non-lisp
    if examineSegment(audioFft, lisp, rest)
        disp("Lisp detected!")
        isLisp = 1;
        return
    elseif examineSegment(audioFft, normal, rest)
        disp("No lisp detected!")
        isLisp = -1;
        return
    end
    % otherwise return 0
    isLisp = 0;
end
