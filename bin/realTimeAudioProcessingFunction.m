function [m] = realTimeAudioProcessingFunction(mode, params)
    % REALTIMEAUDIOPROCESSINGFUNCTION
    %
    % realTimeAudioProcessingFunction("lisp", "1050, 1350", "5500, 6500", "1000, 22050")
    %
    % Params:
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
    

%     %%Spilt Parameters and convert them to double array
%     normalFreqs = strsplit(normalFreqs,',');
%     normalFreqs = str2double(normalFreqs);
% 
%     lispFreqs = strsplit(lispFreqs,',');
%     lispFreqs = str2double(lispFreqs);
%     
%     restFreqs = strsplit(restFreqs,',');
%     restFreqs = str2double(restFreqs);
%     
% 
%     %%Build parameter array to pass it into callAnalyze Function
%     params = [normalFreqs', lispFreqs', restFreqs'];
    
    
    
    %% loop over analyze
    i_and_count = [0, 0];
    

    m = true;

    tic
    while toc

         disp("Schleife läuft");
         x = step(In);
         %y = x;
    
         %step(Out, y);
    
         % actually run the analyze
         i_and_count = callAnalyze(mode, i_and_count, x, params);

         % exit the loop by returning
         if isa(i_and_count, "logical")
             return
         end

    end    
end