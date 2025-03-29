Creacio del registre del llibre:
Intent amb isbn duplicat:
![alt text](image.png)
![alt text](image-11.png)
Intent amb dades correctes:
![alt text](image-2.png)
![alt text](image-3.png)
(A la taula hi ha un registre sense titol, pero ja esta solucionat)
Intent d'inserir un llibre sense titol:
![alt text](image-5.png)
![alt text](image-4.png)
Intent d'inserir un llibre sense data:
![alt text](image-7.png)
![alt text](image-8.png)
Intent d'inserir un llibre amb titol repetit:
![alt text](image-9.png)
![alt text](image-11.png)
Intent d'inserir un llibre amb ISBN incorrecte:
![alt text](image-1.png)
![alt text](image-10.png)
Llista general de llibres:
![alt text](image-6.png)
Cerca llibre per id existent:
![alt text](image-12.png)
Cerca llibre per id inexistent:
![alt text](image-13.png)
Cerca de llibre per titol existent:
![alt text](image-14.png)
Cerca de llibre per titol inexistent:
![alt text](image-15.png)


Preguntes:
Per què al servei estem utilitzant mètodes que no hem declarat explícitament al repositori? Com és possible?
Perque els mètodes que no hem declarat explícitament al repositori venen
proporcionats per CrudRepository sense necessitat de declarar-los,
segons he buscat també crea consultes automatiques per els nostres metodes personalitzats com
find

El repositori pot elegir fer l’extends de les interfícies PagingAndSortingRepository o de JpaRepository. En què es 
diferencien aquestes dues amb la interfície CrudRepository?


Què significa Optional<Classe> i per a què serveix?
és una classe que s'utilitza per indicar que un valor pot estar-hi o no.
Aixì no cal que gestionem comprobant si l'objecte esta buid o no, ho podem
gestionar amb mètodes com ifPresent().

Per què el controlador utilitza el servei i no la seva implementació?

