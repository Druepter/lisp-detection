function [m] = realTimeAudioProcessingFunction(mode, normalFreqs, lispFreqs, restFreqs)
    % config file means no params need to get passed
    % alternatively we do the config file reading separately
    % and pass the params but this is already done at least
    %% initialize audio devices
    % input audio device
    In = audioDeviceReader;
    In.Device = "default";
    
    % set frame window in samples
    frameLength = 0.5; % seconds
    sampleRate = In.SampleRate;
    In.SamplesPerFrame = sampleRate * frameLength;
    
    % output audio device
    Out = audioDeviceWriter;
    Out.Device = "default";
    

    %%Spilt Parameters and convert them to double array
    normalFreqs = strsplit(normalFreqs,',');
    normalFreqs = str2double(normalFreqs);

    lispFreqs = strsplit(lispFreqs,',');
    lispFreqs = str2double(lispFreqs);
    
    restFreqs = strsplit(restFreqs,',');
    restFreqs = str2double(restFreqs);
    

    %%Build parameter array to pass it into callAnalyze Function
    params = [normalFreqs', lispFreqs', restFreqs'];
    
    
    
    %% loop over analyze
    i = 0;
    count = 0;
    

    m = "huhu";

    tic
    while toc

         disp("Schleife läuft");
         x = step(In);
         %y = x;
    
         %step(Out, y);
    
         % actually run the analyze
         i, count = callAnalyze(mode, i, count, x, params);
    end    
end
