BoundedQueue is een queue met een maximale capaciteit.

Doe je een put() terwijl de queue vol is, dan krijg je een OverflowException.
Doe je een take() terwijl de queue leeg is, dan krijg je een UnderflowException.
Het is een fair queue, dus FIFO order.

Deze excepties zijn geen "fouten" maar een indicatie naar de caller. Dit had ook op een andere manier gemodelleerd kunnen worden (null of boolean return value of...).

De huidige implementatie van BoundedQueue is niet thread-safe.

Run BoundedQueueTest (en check de error conditie op regel 30 van die file) en stel vast dat de implementatie echt niet OK is.
NB: dit is slechts één error conditie. Een andere zou zijn om te checken dat de waarden die in de queue gestopt worden en er weer in de correcte FIFO volgorde uit komen.
NB2: BoundedQueueTest laat gebruik van volatile, Thread.yield() en Thread.join() zien.

Jouw eerste opdracht is om 'm wél thread-safe te maken.

Als dat gelukt is, dan is de tweede opdracht om OverflowException en UnderflowException overbodig te maken.
NB: ik bedoel dus niet vervangen door null of boolean return values, maar ECHT overbodig.

HINT: denk aan de hal, de speciale kamer en vooral de WACHTRUIMTE.
