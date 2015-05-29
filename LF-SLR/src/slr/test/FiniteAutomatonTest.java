package slr.test;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import slr.automaton.FiniteAutomaton;
import slr.automaton.State;
import slr.automaton.TransitionMap;
import slr.exception.InvalidTransitionException;
import slr.expression.RegularExpression;

public class FiniteAutomatonTest {

	private FiniteAutomaton automatonA;
	private FiniteAutomaton automatonB;
	private Set<State> automatonAfinalStates;
	private Set<State> automatonAnotFinalStates;
	
	@Before
	public void setUp() throws Exception {
		/**
		 * Autômato A = (a,b)* | #a's é divisível por 3
		 * Autômato B = (a,b)* | 'ab' pertence à sentença
		 */
		TransitionMap aTransitionsA = new TransitionMap();
		TransitionMap bTransitionsA = new TransitionMap();
		TransitionMap cTransitionsA = new TransitionMap();

		State aA = new State("aState", true, aTransitionsA);
		State bA = new State("bState", false, bTransitionsA);
		State cA = new State("cState", false, cTransitionsA);
		
		Set<State> statesA = new TreeSet<State>();
		statesA.add(aA);
		statesA.add(bA);
		statesA.add(cA);
		
		aTransitionsA.add('a', bA);
		aTransitionsA.add('b', aA);
		bTransitionsA.add('a', cA);
		bTransitionsA.add('b', bA);
		cTransitionsA.add('a', aA);
		cTransitionsA.add('b', cA);
		
		this.automatonA = new FiniteAutomaton(statesA, aA);
		this.automatonAfinalStates = new TreeSet<State>();
		this.automatonAfinalStates.add(aA);
		this.automatonAnotFinalStates = new TreeSet<State>();
		this.automatonAnotFinalStates.add(bA);
		this.automatonAnotFinalStates.add(cA);

		TransitionMap aTransitionsB = new TransitionMap();
		TransitionMap bTransitionsB = new TransitionMap();
		TransitionMap cTransitionsB = new TransitionMap();

		State aB = new State("q0", false, aTransitionsB);
		State bB = new State("q1", false, bTransitionsB);
		State cB = new State("q2", true, cTransitionsB);
		
		Set<State> statesB = new TreeSet<State>();
		statesB.add(aB);
		statesB.add(bB);
		statesB.add(cB);
		
		aTransitionsB.add('a', aB);
		aTransitionsB.add('a', bB);
		aTransitionsB.add('b', aB);
		bTransitionsB.add('b', cB);
		cTransitionsB.add('a', cB);
		cTransitionsB.add('b', cB);
		this.automatonB = new FiniteAutomaton(statesB, aB);
		
	}

	@Test
	public void testGetAlphabet() {
		TransitionMap aTransitions = new TransitionMap();
		TransitionMap bTransitions = new TransitionMap();
		TransitionMap cTransitions = new TransitionMap();
		TransitionMap dTransitions = new TransitionMap();

		State a = new State("q0", false, aTransitions);
		State b = new State("q1", false, bTransitions);
		State c = new State("q2", true, cTransitions);
		State d = new State("q3", false, dTransitions);
		
		Set<State> states = new TreeSet<State>();
		states.add(a);
		states.add(b);
		states.add(c);
		states.add(d);
		
		aTransitions.add('a', a);
		aTransitions.add('a', b);
		bTransitions.add('d', c);
		cTransitions.add('a', c);
		cTransitions.add('c', c);
		dTransitions.add('a', a);
		dTransitions.add(RegularExpression.EPSILON, b);
		FiniteAutomaton f = new FiniteAutomaton(states, a);

		assertEquals(true, f.getAlphabet().contains("a"));
		assertEquals(true, f.getAlphabet().contains("c"));
		assertEquals(true, f.getAlphabet().contains("d"));
		assertEquals(3, f.getAlphabet().length());
	}
	
	@Test
	public void testRecognizeDeterministic() {
		assertEquals(false, this.automatonA.recognize("asdbae"));
		assertEquals(false, this.automatonA.recognize("aababbba"));
		assertEquals(true, this.automatonA.recognize(""));
		assertEquals(true, this.automatonA.recognize("aaabb"));
		assertEquals(true, this.automatonA.recognize("abbababbbbbb"));
		assertEquals(true, this.automatonA.recognize("bbbb"));
	}

	@Test
	public void testRecognizeNondeterministic() {
		assertEquals(false, this.automatonB.recognize(""));
		assertEquals(false, this.automatonB.recognize("bbbbaaa"));
		assertEquals(false, this.automatonB.recognize("aaaa"));
		assertEquals(false, this.automatonB.recognize("bb"));
		assertEquals(true, this.automatonB.recognize("ab"));
		assertEquals(true, this.automatonB.recognize("abbbbbbbaa"));
		assertEquals(true, this.automatonB.recognize("aaaaaabb"));
		assertEquals(true, this.automatonB.recognize("bbbbbaaaaab"));
	}

	@Test
	public void testRecognizeNondeterministicEpsilon() {
		TransitionMap aTransitions = new TransitionMap();
		TransitionMap bTransitions = new TransitionMap();
		TransitionMap cTransitions = new TransitionMap();

		State a = new State("q0", false, aTransitions);
		State b = new State("q1", true, bTransitions);
		State c = new State("q2", true, cTransitions);
		
		Set<State> states = new TreeSet<State>();
		states.add(a);
		states.add(b);
		states.add(c);
		
		aTransitions.add(RegularExpression.EPSILON, b);
		aTransitions.add(RegularExpression.EPSILON, c);
		bTransitions.add('a', b);
		cTransitions.add('b', c);
		FiniteAutomaton f = new FiniteAutomaton(states, a);

		assertEquals(false, f.recognize("aaabb"));
		assertEquals(false, f.recognize("ba"));
		assertEquals(true, f.recognize(""));
		assertEquals(true, f.recognize("a"));
		assertEquals(true, f.recognize("aaaaa"));
		assertEquals(true, f.recognize("bbb"));
	}
	
	@Test
	public void testRecognizeNondeterministicEpsilon2() {
		TransitionMap aTransitions = new TransitionMap();
		TransitionMap bTransitions = new TransitionMap();
		TransitionMap cTransitions = new TransitionMap();

		State a = new State("q0", false, aTransitions);
		State b = new State("q1", false, bTransitions);
		State c = new State("q2", true, cTransitions);
		
		Set<State> states = new TreeSet<State>();
		states.add(a);
		states.add(b);
		states.add(c);
		
		aTransitions.add('a', a);
		aTransitions.add(RegularExpression.EPSILON, b);
		bTransitions.add('b', b);
		bTransitions.add(RegularExpression.EPSILON, c);
		cTransitions.add('c', c);
		FiniteAutomaton f = new FiniteAutomaton(states, a);

		assertEquals(false, f.recognize("bbaa"));
		assertEquals(false, f.recognize("cca"));
		assertEquals(false, f.recognize("ccb"));
		assertEquals(true, f.recognize(""));
		assertEquals(true, f.recognize("aa"));
		assertEquals(true, f.recognize("aab"));
		assertEquals(true, f.recognize("abc"));
		assertEquals(true, f.recognize("bb"));
		assertEquals(true, f.recognize("bbcccc"));
		assertEquals(true, f.recognize("c"));
	}

	@Test
	public void testComplete() throws InvalidTransitionException {
		TransitionMap aTransitions = new TransitionMap();
		TransitionMap bTransitions = new TransitionMap();
		TransitionMap cTransitions = new TransitionMap();

		State a = new State("q0", false, aTransitions);
		State b = new State("q1", true, bTransitions);
		State c = new State("q2", true, cTransitions);
		
		Set<State> states = new TreeSet<State>();
		states.add(a);
		states.add(b);
		states.add(c);
		
		aTransitions.add(RegularExpression.EPSILON, b);
		aTransitions.add(RegularExpression.EPSILON, c);
		bTransitions.add('a', b);
		cTransitions.add('b', c);
		FiniteAutomaton f = new FiniteAutomaton(states, a);
		f.complete();

		assertEquals(1, a.transit('a').size());
		assertEquals(1, a.transit('b').size());
		assertEquals(1, b.transit('b').size());
		assertEquals(1, c.transit('a').size());
	}
	
	@Test
	public void testDeterminize() {
		// TODO
	}

	@Test
	public void testMinimize() {
		// TODO
	}

	@Test
	public void testIsComplete() {
		TransitionMap aTransitions = new TransitionMap();
		TransitionMap bTransitions = new TransitionMap();
		TransitionMap cTransitions = new TransitionMap();

		State a = new State("q0", false, aTransitions);
		State b = new State("q1", true, bTransitions);
		State c = new State("q2", true, cTransitions);
		
		Set<State> states = new TreeSet<State>();
		states.add(a);
		states.add(b);
		states.add(c);
		
		aTransitions.add(RegularExpression.EPSILON, b);
		aTransitions.add(RegularExpression.EPSILON, c);
		bTransitions.add('a', b);
		cTransitions.add('b', c);
		FiniteAutomaton f = new FiniteAutomaton(states, a);
		
		assertEquals(false, f.isComplete());
		f.complete();
		assertEquals(true, f.isComplete());		
	}

	@Test
	public void testIsDeterministic() {
		assertEquals(true, this.automatonA.isDeterministic());
		assertEquals(false, this.automatonB.isDeterministic());

		TransitionMap transitions = new TransitionMap();
		State a = new State("test", false, transitions);
		Set<State> states = new TreeSet<State>();
		states.add(a);
		this.automatonA.getInitialState().getTransitions().put(RegularExpression.EPSILON, states);
		assertEquals(false, this.automatonA.isDeterministic());		
	}

	@Test
	public void testIsMinimal() {
		// TODO
	}
	
	@Test
	public void testUnion() {
		FiniteAutomaton union = this.automatonA.union(this.automatonB);
		
		assertEquals(false, union.recognize("aa"));
		assertEquals(false, union.recognize("bbba"));
		assertEquals(true, union.recognize(""));
		assertEquals(true, union.recognize("aaa"));
		assertEquals(true, union.recognize("ab"));
		assertEquals(true, union.recognize("bbaab"));
		assertEquals(true, union.recognize("bbaaabb"));
	}
	
	@Test
	public void testIntersection() {
		FiniteAutomaton intersection = this.automatonA.intercection(this.automatonB);

		assertEquals(false, intersection.recognize(""));
		assertEquals(false, intersection.recognize("bbbb"));
		assertEquals(false, intersection.recognize("aaa"));
		assertEquals(false, intersection.recognize("bbaa"));
		assertEquals(false, intersection.recognize("bbaab"));
		assertEquals(true, intersection.recognize("abaa"));
		assertEquals(true, intersection.recognize("aaabbbb"));
		assertEquals(true, intersection.recognize("bababaabbaa"));
	}

	@Test
	public void testComplement() {
		FiniteAutomaton automatonComplement = this.automatonA.complement();
		assertEquals(this.automatonAnotFinalStates, automatonComplement.getFinalStates());
		assertEquals(this.automatonAfinalStates, automatonComplement.getNotFinalStates());
	}
	
}
