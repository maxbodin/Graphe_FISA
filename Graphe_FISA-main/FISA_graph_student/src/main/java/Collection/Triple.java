package Collection;

public class Triple<A, B, C> {
	private  A first;
	private  B second;
	private C third;

	public Triple(A left, B middle, C right) {
		this.first = left;
		this.second= middle;
		this.third = right;
	}

	public A getFirst() {
		return first;
	}

	public B getSecond() {
		return second;
	}

	public C getThird() {
		return third;
	}

	public void setFirst(A val) {
		this.first = val;
	}

	public void setSecond(B val) {
		this.second = val;
	}

	public void setThird(C val) {
		this.third = val;
	}

	public void setTriple(Triple<A,B,C> t) {
		this.first = t.getFirst();
		this.second = t.getSecond();
		this.third = t.getThird();
	}

	@Override
	public int hashCode() {
		return first.hashCode() ^ second.hashCode() ^ third.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Triple<?, ?, ?>)) {
			return false;
		}
		Triple<?, ?, ?> triplo = (Triple<?, ?, ?>) o;
		return this.first.equals(triplo.getFirst())
			&& this.second.equals(triplo.getSecond())
			&& this.third.equals(triplo.getThird());
	}

	@Override
	public String toString(){
		return "<"+first+","+second+","+third+">";
	}

}
