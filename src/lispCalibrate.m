function [name, freqs, restName, restFreqs] = lispCalibrate(In, name)
    % LISPCALIBRATE calibrate lisp detection for a voice
    %
    % lispCalibrate(audioDeviceReader)
    %
    % Params:
    % * In:   audioDeviceReader object
    % * name: name to be given to freqs
    %
    % Returns:
    % * name:      name of frequencies
    % * freqs:     detected frequencies
    % * restName:  "restFreqs
    % * restFreqs: band pass frequencies

    In.SamplesPerFrame = In.SampleRate * 2; % 2s recordings

    % this is just a band pass from 1000 Hz to the highest frequency
    restFreqs = [1000, round(In.SampleRate / 2)];
    restName = "restFreqs";

    audio = step(In);

    freqs = lispCalibrateSegment(audio, restFreqs);
end