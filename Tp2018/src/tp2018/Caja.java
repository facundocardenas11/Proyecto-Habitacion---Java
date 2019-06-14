package tp2018;


public class Caja {
	
	//Variables de instancia
	private int _ancho;
	private int _largo;
	private int _elemento;
	
	//Getters
	
	public int get_ancho() {
		return _ancho;
	}
	
	public int get_largo() {
		return _largo;
	}
	
	public int get_elemento() {
		return _elemento;
	}
	
	//Constructor
	public Caja( int ancho, int largo, int elemento) {
	
	if (ancho >0 && largo>0){
		_ancho = ancho;
		
		_largo = largo;
		
		_elemento = elemento;
	}
	else
			throw new RuntimeException("Valores no validos");
	
	}
	
	//Metodos
	
	@Override
	public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Caja [_ancho=");
	builder.append(_ancho);
	builder.append(", _largo=");
	builder.append(_largo);
	builder.append(", _elemento=");
	builder.append(_elemento);
	builder.append("]");
	return builder.toString();
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _ancho;
		result = prime * result + _largo;
	return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
		     return false;
		
		Caja other = (Caja) obj;
		
		if (_ancho != other._ancho)
			return false;
		
		if (_elemento != other._elemento)
			return false;
		
		if (_largo != other._largo)
			return false;
		
		return true;
	}

	public int area(){
		
		return _ancho*_largo;
	}


 }//Bloque de la clase

	
	
 
