We willen meten hoe lang het duurt om een bepaalde CPU-intensieve taak uit te voeren en hoe dit 
varieert aan de hand van de concurrency.

Dit kan door N threads te starten en die allemaal dezelfde taak (cpuIntensiveTask) uit te laten voeren.
Het is belangrijk dat de N threads *tegelijkertijd* lopen, we willen zeker weten dat de concurrency tijdens 
de meting daadwerkelijk N is.

De huidige implementatie garandeert dit niet, het start simpelweg in een loop N threads die dan meteen aan 
hun taak beginnen. Het kan met deze implementatie dus gebeuren dat de eerst-gestarte workerthreads al klaar 
zijn terwijl we nog in de thread-starting-loop bezig zijn workerthreads te starten (dit hangt af van de 
doorlooptijd van de taak, het aantal threads dat gestart moeten worden, en het aantal beschikbare CPUs/cores).

Met andere woorden: fix dit! Zorg dat dit altijd werkt, ongeacht de doorlooptijd van de taak, het aantal te
starten threads, en het aantal cores/CPUs.

