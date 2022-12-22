function [i_and_count] = callAnalyze(mode, i_and_count, audio, params)
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
        maxIterations = 10;
        alarmText = "Lots of lisping!";

        % +1 if lisp, -1 if no lisp, 0 else  | NORMAL        LISP          REST (bandpass)
        i_and_count(2) = i_and_count(2) + lispAnalyze(audio, params(:, 1), params(:, 2), params(:, 3));
    else
        error("Unknown mode passed!")
    end
    % check if we've hit max iterations and check for our condition
    if i_and_count(1) == maxIterations
        if i_and_count(2) > 0
            disp(alarmText) % this should eventually be replaced by a uialert
            playSound(i_and_count(2) * 0.25); % audio notification: count * 0.25 s
        end
        % now we reset counter and iteration
        i_and_count = [0, 0];
    end
end
