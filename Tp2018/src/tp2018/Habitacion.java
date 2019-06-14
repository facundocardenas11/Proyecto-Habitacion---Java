package tp2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.Iterable;
import java.util.Map.Entry;

public class Habitacion implements Iterable<Caja> {
	
	//Variables de instancia 
	private int _ancho;
	private int _largo;
	private HashMap<Coordenada, Caja> _cajas;
	boolean[][] _habitacion;
	
	//Constructor
	public Habitacion(int ancho, int largo) {
		
	if (ancho >0 && largo >0){
	 _ancho = ancho;
	 _largo = largo;
	 _cajas = new HashMap<Coordenada, Caja>();
	 _habitacion = new boolean[largo][ancho];
	 
	 }
	 else
	 {
			throw new RuntimeException("Valores no validos");
	 }	
	}
	
	//Metodos (publicos)

	public boolean puedoAgregarCaja(int ancho, int largo) {  //verifica si puede agregar una caja en la habitacion				
		Coordenada c = new Coordenada(0,0);
		
		return factibilidad(ancho, largo, c);
	}
	
	public void agregarCaja(int ancho, int largo, int elemento) {  //agrega una caja en la habitacion
		Coordenada c = new Coordenada(0,0);
		
		if (factibilidad(ancho, largo, c)) 
		{
	//"pinto" la caja
			for (int i=c.y; i<c.y+largo; i++)
			{
				for (int j=c.x; j<c.x+ancho; j++) {
	                   _habitacion[i][j] = true;
	              }
	         }
	        _cajas.put(c, new Caja( ancho, largo, elemento));
	    }   
		else
	    {
			throw new RuntimeException("Caja infactible!!!");
	     }
	}
	
	public int cantCajas() {
	return _cajas.size();
	}
	
	public int reacomodarCajas() {  //Reacomoda las cajas en la habitacion (para maximizar el aprovechamiento del espacio)
		
		int cont=0;
		//Creamos un nuevo arrraylist de caja
		ArrayList<Caja> CajasAuxiliar= new ArrayList<Caja>();
		
		//Iteramos a traves de la variable "_cajas" (hashMap)
		Iterator<Entry<Coordenada, Caja>> it1= this._cajas.entrySet().iterator();
		
		//Agregamos las cajas de la habitacion a "c"
		while(it1.hasNext()) {
			
			Entry<Coordenada,Caja> aux=it1.next();
				
			CajasAuxiliar.add(aux.getValue());
		}
		
		//Ordena las cajas de la lista de la mas grande a la mas chica 
		Habitacion.ordenarMayorAMenor(CajasAuxiliar);
		
		//Quita todas las cajas de la habitacion
		this.vaciarHabitacion();		
		//Agrega las cajas (ordenadas) a la habitacion (vacia)
		Iterator<Caja> it2= CajasAuxiliar.iterator();
		
		while(it2.hasNext()) {
			
			Caja cajaAux=it2.next();
			if(this.puedoAgregarCaja(cajaAux.get_ancho(),cajaAux.get_largo())) {
				
				this.agregarCaja(cajaAux.get_ancho(),cajaAux.get_largo(),cajaAux.get_elemento());	
				//Incrementa el contador cada vez que pudo agregar una caja
				cont++;
			}
			else
			{
				return -1;// devuelve -1 porque no es posible agregar todas las cajas en la habitacion.
			}
		}
	 
		return cont; //valor que representa la cantidad de cajas que pudo acomodar
	}
	
	@Override
	public boolean equals(Object obj) {  //evalua si una habitacion es igual a otra
	   if (this == obj)
		   	return true;
	   
	   if (obj == null)
	       return false;
	   
	   if (getClass() != obj.getClass())
	          return false;
	   
	  Habitacion other = (Habitacion) obj;
	  
	  if (_ancho != other._ancho)
		  
	      return false;
	  
	  if (_largo != other._largo)
		  
	       return false;
	  if (_cajas.size() != other._cajas.size())
		  
	          return false;
	  
	  for (Coordenada c: _cajas.keySet()) {
		  
	      if (other._cajas.keySet().contains(c)) {
	// tengo la misma coordenada en la otra hab,
	// tengo q ver si es la misma caja
	        if (!_cajas.get(c).equals(other._cajas.get(c))) {
	// la caja era distinta
                   return false;
	               }
       	   }
         
	      
	     else
	    	 
          {
	    	 return false;
	      }
	  }
	return true;
	
	}
	
	@Override
	public String toString() {  //imprime la habitacion
	 StringBuilder builder = new StringBuilder();
	  builder.append("ancho="); 
	  builder.append(_ancho);
	  builder.append(", largo=");
	  builder.append(_largo);
	  builder.append("\n");
	  for (int i=0; i<_largo; i++) {
		  
		  for (int j=0; j<_ancho; j++) {
			  builder.append(_habitacion[i][j] ? 1:0);
     	}
	  builder.append("\n");
	  }
	
	 return builder.toString();
	 }

	public boolean puedoPasarCajas(Habitacion h1) {  //devuelve true o false segun si es posible pasar todas las cajas de una habitacion a otra
		
		if (this.espacioDisponible()>h1.espacioOcupado()){
			
			boolean ret=true;
			
			Iterator<Entry<Coordenada, Caja>> it= this._cajas.entrySet().iterator();
		
			while(it.hasNext()) {
				Caja aux= it.next().getValue();
				ret= ret && puedoAgregarCaja(aux.get_ancho(), aux.get_largo());
			}
		
			return ret;
		}
		
		return false;
			
		}
	
	public boolean estaVacia() {   //Devuelve true o false segun si en la habitacion hay cajas o no
		
		return espacioOcupado()==0;
	}	

	public int espacioOcupado() {  //Retorna un valor que es resultado de la suma del tamaño de todas las cajas
		int aux=0;
		
		Iterator<Entry<Coordenada, Caja>> it= this._cajas.entrySet().iterator();
	
		while(it.hasNext()) {
			
			aux= aux+it.next().getValue().area();
		}
	
		return aux;
	}
	
	public int tamanio() {  //devuelve el tamanio de la habitacion 
		
		return _ancho*_largo;
	}
	
	@Override
    
	public Iterator<Caja> iterator(){  //este metodo devuelve cada caja de cajas si es que existe con el values.
	    	
		return this._cajas.values().iterator();
	}
	    	
	//Metodos privados/auxiliares
	
	private boolean factibilidad(int ancho, int largo, Coordenada c) {  
		if (ancho > _ancho || largo > _largo)
		   return false;
		//busco el 1er lugar posible
		c.x = 0;
		c.y = 0;
		
		boolean ok = false;
		//busca una coordanada factible y la asigna.
		
		while (!ok && c.y < _largo){
		
			ok = fact2(c.x,c.y, ancho, largo);
		    if (!ok) incrementar(c);
		
		 }
		return ok;
		
	   }

	private void incrementar(Coordenada c) { 
			if (c.x < _ancho-1)
			    c.x=c.x+1;
			else
			 {
	     		c.x=0;
		    	c.y=c.y+1;
			 }
	}

	private boolean fact2(int x ,int y, int ancho,int largo) { 
			boolean ret = false;
		   
			if (x+ancho < _ancho && y+largo < _largo) {
			           ret = true;
		      	for (int i=y; i<y+largo; i++) 
		      	{
			      for (int j=x; j<x+ancho; j++) {
			         ret = ret && !_habitacion[i][j];
			              }
			     }
			 }
			return ret;
			}

	private int espacioDisponible() {  //Devuelve un valor que representa el espacio que hay disponible en la habitacion
			
			return this.tamanio()-this.espacioOcupado();
			
		}
		
	private void vaciarHabitacion() //"Quita" a todas las cajas de la habitacion 
	{
		
		//Creamos una nueva habitacion (vacia)
		this._cajas= new HashMap<Coordenada,Caja>();
		this._habitacion= new boolean [this._largo][this._ancho];
	}
	
		
	public static void ordenarMayorAMenor(ArrayList<Caja> c) {  //ordeno las cajas (contenidas en un ArrayList) por su tamaño de mayor a menor
			// TODO Auto-generated method stub
			Collections.sort(c,new Comparator<Caja>() {
				@Override
				public int compare(Caja c1 , Caja c2) {
					return new Integer(c2.area()).compareTo(new Integer(c1.area()));
				}
			});
	}

	//Algunos toString()
	
	public String las3MasGrandes(){  //Muestra por pantalla las 3 cajas mas grande de la habitacion
		
		ArrayList<Caja> c= new ArrayList<Caja>();
		
		Iterator<Entry<Coordenada, Caja>> it1= this._cajas.entrySet().iterator();
		
		while(it1.hasNext()) {
			
			Caja aux= it1.next().getValue();
						
					c.add(aux);
		}
				
	 Habitacion.ordenarMayorAMenor(c);
	 
	 Iterator<Caja> it2= c.iterator();
	 
	 int i=0;
	 
	 StringBuilder sb= new StringBuilder();
	 
	 sb.append("Las 3 cajas mas grandes son: "+"\n");
	 
	 while (it2.hasNext() && i<=2) {
		 
		 Caja aux2= it2.next();
		 
		 sb.append("."+aux2.toString()+"\n");
		 
		 i++;
	 }
	return sb.toString();
	
	}
	
	public String tamanioHabitacion() //Muestra por pantalla el tamanio total de la habitacion
	{
		
		StringBuilder sb= new StringBuilder();
		sb.append("La habitacion mide ");
		sb.append(this.tamanio());
		sb.append(" metros cuadrados");
		return sb.toString();
	}
	
	public String espacioLibre() {
		
		StringBuilder sb= new StringBuilder();
		sb.append("Quedan ");
		sb.append(this.espacioDisponible());
		sb.append(" metros cuadrados libres en la habitacion");
		return sb.toString();
	}

	public String espacioUtilizado() {
		
		if (estaVacia()) {
			
			return "La habitacion esta vacia!";
		}
	
		else {
			
			StringBuilder sb= new StringBuilder();
			sb.append("El espacio ocupado por las cajas es de ");
			sb.append(this.espacioOcupado());
			sb.append(" metros cuadrados");
			return sb.toString();
			
		}
	}

}//Bloque de la clase 

