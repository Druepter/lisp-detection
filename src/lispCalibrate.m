function [name, freqs] = lispCalibrate(In, name)
    % LISPCALIBRATE calibrate lisp detection for a voice
    %
    % lispCalibrate(audioDeviceReader, "normalFreqs")
    %
    % Params:
    % * In:   audioDeviceReader object
    % * name: name of the parameter
    %
    % Returns:
    % * name:      name of frequencies
    % * freqs:     detected frequencies
    % * restName:  "restFreqs"
    % * restFreqs: band pass frequencies
     
    % 2s recordings
    In.SamplesPerFrame = In.SampleRate * 2;

    % this is just a band pass from 1000 Hz to the highest frequency
    restFreqs = [1000, round(In.SampleRate / 2)];

    audio = step(In);

    freqs = lispCalibrateSegment(audio, restFreqs);
end
