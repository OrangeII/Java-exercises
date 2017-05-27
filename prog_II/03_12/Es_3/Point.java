abstract class Point{
	protected double x, y;
}

class MutablePoint extends Point{

	public MutablePoint(float x, float y){
		this.x = x;
		this.y = y;
	}

	public void translate(double dx, double dy){
		this.x += dx;
		this.y += dy;
	}

	public void rotate(double angle){
		double nx = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double ny = this.y * Math.sin(angle) + this.x * Math.cos(angle);
		this.x = nx;
		this.y = ny;
	}

	public double distance(MutablePoint p){
		assert p != null;
		return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
	}
}

class ImmutablePoint extends Point{

	public ImmutablePoint(double x, double y){
		this.x = x;
		this.y = y;
	}

	public ImmutablePoint translate(double dx, double dy){
		return new ImmutablePoint(this.x + dx, this.y+dy);
	}

	public ImmutablePoint rotate(double angle){
		double nx = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double ny = this.y * Math.sin(angle) + this.x * Math.cos(angle);
		return new ImmutablePoint(nx, ny);
	}

	public double distance(ImmutablePoint p){
		assert p != null;
		return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
	}
}
