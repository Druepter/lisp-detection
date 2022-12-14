function [iteration, counter] = callAnalyze(mode, iteration, counter, audio, parameters)
    iteration = iteration + 1;

    if mode == "lisp"
        maxIterations = 10;
        alarmText = "Lots of lisping!";

        % +1 if lisp, -1 if no lisp, 0 else  | NORMAL        LISP          REST (bandpass)
        counter = counter + lispAnalyze(audio, params(:, 1), params(:, 2), params(:, 3));
    else
        error("Unknown mode passed!")
    end
    % check if we've hit max iterations and check for our condition
    if iteration == maxIterations
        if counter > 0
            disp(alarmText) % this should eventually be replaced by a uialert
        end
        % now we reset counter and iteration
        counter = 0;
        iteration = 0;
    end
end
