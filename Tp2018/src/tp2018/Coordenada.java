package tp2018;


public class Coordenada {
	
	//Variables de instancia
	public int x;
	public int y;

	//Constructor
	public Coordenada(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	//Metodos
	@Override
	public String toString(){
		return x+","+y;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + x;
		result = prime * result + y;
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
		Coordenada other = (Coordenada) obj;
		
		if (x != other.x)
			return false;
		
		if (y != other.y)
			return false;
		
		return true;
	}

}
