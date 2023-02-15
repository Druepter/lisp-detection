function isLisp = lispAnalyze(audio, normal, lisp)
    % LISPANALYZE Analyze audio array for lisping
    % 
    % lispAnalyze(audio, [1050, 1350], [5500, 6500], [1000, 22050])
    % 
    % Parameters:
    % * audio:  audio array to be analyzed
    % * normal: correct pronunciation's frequencies
    % * lisp:   lisp pronunciation's frequencies
    %
    % Returns:
    % * isLisp, either 0 for nothing detected, 1 for lisp detected, or -1
    %   for correct pronunciation detected.
   
    % converting params to match required form
    normal = strsplit(normal,',');
    normal = str2double(normal);

    lisp = strsplit(lisp,',');
    lisp = str2double(lisp);

    % audioDeviceReader defaults to 22050 anyway so we don't have to save this
    % this is the bandpass to be applied
    rest = [1000 22050];
    % rest = strsplit(rest,',');
    % rest = str2double(rest);

    % use standard deviation to detect speech
    % the julia script used a larger audio file as reference
    % and compared segment means to the full audio file's to
    % determine silence but this is easier on memory
    % an alternative to this would be a noise gate or a
    % reference mean found during calibration
    if std(audio) < 0.02
        disp("Er geht hier rein")
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
