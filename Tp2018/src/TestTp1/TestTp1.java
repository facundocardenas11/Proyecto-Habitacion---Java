package TestTp1;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import tp2018.Caja;
import tp2018.Habitacion;

import org.junit.Before;
//import java.util.Iterator;

public class TestTp1 {
	
	private Habitacion h1, h2, h3, h4;
	
	@Before
	public void setUp() {
	h1 = new Habitacion(10,20);
	h1.agregarCaja(3, 3, 4);
	h1.agregarCaja(3, 4, 4);
	h1.agregarCaja(3, 5, 4);
	h2 = new Habitacion(20,15);
	h2.agregarCaja(3, 3, 4);
	h3 = new Habitacion(10,20);
	h3.agregarCaja(3, 3, 4);
	h3.agregarCaja(3, 4, 4);
	h3.agregarCaja(3, 5, 4);
	h4 = new Habitacion(6,6);
	
	}
	
	@Test
	public void testHabitacion() {
	//h1 tiene que ser igual que h3 y distinto que h2
	assertTrue(h1.equals(h3));
	assertTrue(!h1.equals(h2));
	}
	
	
	@Test
	public void testPuedoAgregarCaja() {
	// no puedo agregar cualquier caja
	assertTrue(!h1.puedoAgregarCaja(100, 10));
	}
	
	
	@Test
	public void testAgregarCaja() {
	int cant = h1.cantCajas();
	h1.agregarCaja(1, 1, 2);
	// cambio la cantidad de cajas
	assertTrue(h1.cantCajas() != cant);
	}
	
	@Test
	public void testReacomodarCajas() {
	
	int cant= h3.cantCajas();
	int aux= h3.reacomodarCajas();//la cantidad que se reacomoda debe estar entre 0 y cant 
	assertTrue(aux>=0 && aux<=cant);
	//reacomodar no pierde cajas 
	assertTrue(h3.cantCajas()== cant);
	}
	
	@Test
	public void testForEachCajas() {
	h1.agregarCaja(2, 3, 4);
	h1.agregarCaja(3, 4, 5);
	h1.agregarCaja(4, 5, 6);
	int area_caja_mayor = 0;
	for (Caja c: h1) {
	area_caja_mayor = Math.max(area_caja_mayor, c.area());
	}
	assertEquals(area_caja_mayor,20);
	}
	
  
	@Test
	public void testIterCajas() {
	Caja c;
	int area_caja_mayor = 0;
	Iterator<Caja> it = h1.iterator();
	while (it.hasNext()) {
	c = it.next();
	area_caja_mayor = Math.max(area_caja_mayor, c.area());
	}
	assertEquals(area_caja_mayor,15);
	}
	
	
	@Test
	public void testPuedoPasarCajas() {
	// Guardo la cantidad actual de cajas
	int cant = h4.cantCajas();
	// Se espera False, en h4 no entran todas las cajas de h3
	assertFalse(h4.puedoPasarCajas(h3));
	// Se espera True, ya que en h2 hay una sola caja y no es grande
	assertTrue(h4.puedoPasarCajas(h2));
	// Verifico que h4 tenga la misma cantidad de cajas que antes
	assertEquals(h4.cantCajas(),cant);
	}


}
