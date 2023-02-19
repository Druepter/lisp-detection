function [i_and_count] = realTimeAudioProcessingFunction(mode, params)
    % REALTIMEAUDIOPROCESSINGFUNCTION
    %
    % realTimeAudioProcessingFunction("lisp", [1050, 5500; 1350, 6500])
    %
    % realTimeAudioProcessingFunction("noisegate", 0.1)
    %
    % Params:
    % * mode:   audio processing mode [lisp, noisegate]
    % * params: parameters for audio processing, mode-dependent
    %
    % Returns:
    % * i_and_count: number of detections once enough detections are made
    In = audioDeviceReader; % input audio device

    In.Device = "default";
    
    % set frame window in samples
    frameLength = 0.5; % seconds
    sampleRate = In.SampleRate;
    In.SamplesPerFrame = sampleRate * frameLength;
    
    % output audio device
    Out = audioDeviceWriter;
    Out.Device = "default";
    
%% This was how we read the params for lisp detected - leaving here for now
%     %%Split Parameters and convert them to double array
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
    

    firstRun = true;

    %mode = "noisegate";

    tic
    while toc

         %get variable from Matlab Workspace   
         %varName = evalin('base', 'stopFunction');

        % disp("Variable aus Workspace: " + varName);   

         disp("Schleife läuft");
         x = step(In);
         %y = x;
    
         %step(Out, y);

         % make sure we're not on the first run

         if firstRun == false
             % if we aren't then we can fetch the previous run's output
             i_and_count = fetchOutputs(f);
         else
             % note we're no longer on first run
             firstRun = false;
         end

         % exit the loop by returning

         if length(i_and_count) == 1
             playSound(i_and_count * 1); % audio notification: count * 1 s
             return
         end

         % run analysis
         % this is done in the background so we can keep recording while
         % processing (useful for slower analysis scripts)

         f = parfeval(backgroundPool, @callAnalyze, 1, mode, ...
             i_and_count, x, params);


         %i_and_count = callAnalyze(mode, i_and_count, x, params);

    end    
end
