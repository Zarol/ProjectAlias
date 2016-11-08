package com.zarol.projectalias.components;

import com.zarol.projectalias.framework.Component;

/**
 * @author Zarol
 */
public class ScorableComponent extends Component{
	private int value;

	public ScorableComponent() {
		value = 100;
	}

	public ScorableComponent(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
