function [normalFreqs, lispFreqs, restFreqs] = lispCalibrate(In)
    % LISPCALIBRATE calibrate lisp detection for a voice
    %
    % lispCalibrate(audioDeviceReader)
    %
    % Params:
    % * In: audioDeviceReader object
    %
    % Returns:
    % * normalFreqs: normal S frequencies
    % * lispFreqs:   lisped S frequencies
    % * restFreqs:   band pass frequencies

    In.SamplesPerFrame = In.SampleRate * 2; % 2s recordings
    disp("For the next 3 seconds, make a normal S sound.")
    pause(0.5);

    % this is just a band pass from 1000 Hz to the highest frequency
    restFreqs = [1000, round(In.SampleRate / 2)];

    audio = step(In);

    normalFreqs = lispCalibrateSegment(audio, restFreqs);
    
    disp("For the next 3 seconds, make a lisped S sound.")
    pause(0.5);

    audio = step(In);

    lispFreqs = lispCalibrateSegment(audio, restFreqs);
end