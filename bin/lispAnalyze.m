function isLisp = lispAnalyze(audio, normal, lisp, rest)
    % LISPANALYZE Analyze audio array for lisping
    % 
    % lispAnalyze(audio, [1050, 1350], [5500, 6500], [1000, 22050])
    % 
    % Parameters:
    % * audio:  audio array to be analyzed
    % * normal: correct pronunciation's frequencies
    % * lisp:   lisp pronunciation's frequencies
    % * rest:   all frequencies to analyze, used as a bandpass
    %
    % Returns:
    % * isLisp, either 0 for nothing detected, 1 for lisp detected, or -1
    %   for correct pronunciation detected.
    disp("analyzing...")

    % use standard deviation to detect speech
    % the julia script used a larger audio file as reference
    % and compared segment means to the full audio file's to
    % determine silence but this is easier on memory
    % an alternative to this would be a noise gate or a
    % reference mean found during calibration
    if std(audio) < 0.02
        isLisp = 0;
        return
    end

    audioFft = fft(audio);

    % return 1 if this segment is a lisp and -1 if non-lisp
    if examineSegment(audioFft, lisp, rest)
        isLisp = 1;
        return
    elseif examineSegment(audioFft, normal, rest)
        isLisp = -1;
        return
    end
    % otherwise return 0
    isLisp = 0;
end
