# **Mine Sweeper**

Projeto simples de Campo Minado (Mine Sweeper) que mostra tratamento de erros, orientação a objetos, utilização de streams, lambdas, encapsulamento, e recursividade.

Parte do código da classe Field (Campo) que mostra a lógica da função abrir(open) com visibilidade default para ficar visível apenas dentro do modelo.

```java
boolean open() {
	if(!open && !marked) {
		open = true;
		
		if(mine) {
			throw new ExceptionExplosion();
		}
		
		if(safeNeighborhood()) {
			neighbors.forEach(n -> n.open());
		}
		return true;
	} else {
		return false;
	}
}

boolean safeNeighborhood() {
	return neighbors.stream().noneMatch(n -> n.mine);
```
