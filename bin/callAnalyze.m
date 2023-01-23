function [i_and_count] = callAnalyze(mode, i_and_count, audio, params)

    disp(mode);

    % CALLANALYZE Call an analysis mode
    % 
    % Parameters:
    % * mode: Analysis mode as string, currently only "lisp" is supported.
    % * i_and_count: Array containing current iteration and counter status.
    % * audio: Audio array to be analyzed.
    % * params: Analysis parameters.
    % 
    % Returns:
    % * i_and_count either increased or reset
    i_and_count(1) = i_and_count(1) + 1;

    if mode == "lisp"
        disp("callAnalyze");
        maxIterations = 10;
        % alarm texts can't really be sent out via a backgroundPool worker
        % alarmText = "Lots of lisping!";
            
        disp("Paramter im callAnalyze normal: " + params(1));
         disp("Paramter im callAnalyze normal mit :: " + params(:, 1));
        disp("Paramter im callAnalyze lsip: " + params(2));
        disp("Paramter im callAnalyze rest: " + params(3));

        % +1 if lisp, -1 if no lisp, 0 else  | NORMAL        LISP          REST (bandpass)
        i_and_count(2) = i_and_count(2) + lispAnalyze(audio, params(:, 1), params(:, 2), params(:, 3));
    elseif mode == "noisegate"
        maxIterations = 1;
        % alarmText = "Volume above noise gate!";

        i_and_count(2) = noiseGateAnalyze(audio, params(1));

    else
        error("Unknown mode passed!")
    end
    % check if we've hit max iterations and check for our condition
    if i_and_count(1) == maxIterations
        if i_and_count(2) > 0
            % for testing purposes return true
            i_and_count = i_and_count(2);
            return
        end
        % now we reset counter and iteration
        i_and_count = [0, 0];
    end
end
