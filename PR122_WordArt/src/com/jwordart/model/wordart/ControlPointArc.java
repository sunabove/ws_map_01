package com.jwordart.model.wordart;

/**
 * 타원 아크를 따라서 움직이는 콘트롤 포인트이다.
 */
public class ControlPointArc extends ControlPoint {
	private double currentAngle = 0; // 현재 각도
	private double currentRadiusRatio = 0; // 현재 반경 비율(외곽 타원 반경 기준)
	private double minRadiusRatio = 0; // 최소 반경 비율(외곽 타원 반경 기준

	/**
	 * 생성자
	 *
	 * @param wa
	 *            워드 아트 currentRadiusRatio 현재 반경 비율 minRadiusRatio 최소 반경 비율
	 *            currentAngle 현재 각도
	 */
	public ControlPointArc(WordArt wa, double currentRadiusRatio,
			double minRadiusRatio, double currentAngle) {
		super(wa);

		this.currentRadiusRatio = currentRadiusRatio;
		this.minRadiusRatio = minRadiusRatio;
		this.currentAngle = currentAngle;

		this.reset();
	}

	/**
	 * 현재 반경 비율과 현재 각도에 맞추어 콘트롤 포인트의 좌표를 재 설정한다. 워드 아트의 크기가 바뀌면 자동으로 콘트롤 포인트의
	 * 좌표가 같은 배율로 설정 되어진다.
	 */
	@Override
	public void reset() {
		double a0 = super.getWordArtWidth() / 2.0, // a*a/x/x + b*b/y/y = 1
		b0 = super.getWordArtHeight() / 2.0;

		double theta = this.currentAngle;

		double cos = Math.cos(theta), sin = Math.sin(theta);

		double r = 1.0 / Math.sqrt(cos * cos / a0 / a0 + sin * sin / b0 / b0); // current
																				// radius

		r *= this.currentRadiusRatio;

		double x = r * cos, y = r * sin;

		x = x + a0 - ControlPointArc.defaultWidth / 2.0;
		y = y + b0 - ControlPointArc.defaultHeight / 2.0;

		super.setRect(x, y, ControlPoint.defaultWidth,
				ControlPoint.defaultHeight);
	}

	/**
	 * 현재 각도를 리턴한다.
	 */
	public double getCurrentAngle() {
		return this.currentAngle;
	}

	/**
	 * 현재 반경 비율을 리턴한다.
	 */
	public double getCurrentRadiusRatio() {
		return this.currentRadiusRatio;
	}

	/**
	 * 최소 반경 비율을 리턴한다.
	 */
	public double getMinRadiusRatio() {
		return this.minRadiusRatio;
	}

	/**
	 * 본 클래스의 클론(Clone) 메소드이다.
	 */
	@Override
	public ControlPoint create(WordArt wa) {
		ControlPointArc cp = new ControlPointArc(wa, this
				.getCurrentRadiusRatio(), this.getMinRadiusRatio(), this
				.getCurrentAngle());
		cp.reset();
		return cp;
	}

	/**
	 * 콘트롤 포인트의 중암점을 가로 자표를 리턴한다.
	 */
	@Override
	public double getCenterX() {
		return super.getX() + ControlPoint.defaultWidth / 2.0;
	}

	/**
	 * 콘트롤 포인트의 중암 세로 좌표를 리턴한다.
	 */
	@Override
	public double getCenterY() {
		return super.getY() + ControlPoint.defaultHeight / 2.0;
	}

	/**
	 * 콘트롤 포인트를 이동한다.
	 *
	 * @param dx
	 *            가로 이동 간격 dy 세로 이동 간격
	 */
	@Override
	public void moveBy(double dx, double dy) {
		if (super.getWordArt().isVertical()) {
			dy = -dy;
		}

		double a0 = super.getWordArtWidth() / 2.0, b0 = super
				.getWordArtHeight() / 2.0;

		double x = this.getCenterX() + dx, y = this.getCenterY() + dy;

		x -= a0;
		y -= b0;

		if (x == 0 && y == 0) {
			this.currentRadiusRatio = 0;
			this.currentAngle = -Math.PI;
		} else if (x * x / a0 / a0 + y * y / b0 / b0 < this.minRadiusRatio) {
			this.currentRadiusRatio = this.minRadiusRatio;
			this.currentAngle = com.jwordart.util.WordArtUtil.getAngle(x,
					y);
		} else if (x * x / a0 / a0 + y * y / b0 / b0 < 1.0) {
			this.currentRadiusRatio = Math.sqrt(x * x / a0 / a0 + y * y / b0
					/ b0);
			this.currentAngle = com.jwordart.util.WordArtUtil.getAngle(x,
					y);
		} else {
			this.currentRadiusRatio = 1.0;
			this.currentAngle = com.jwordart.util.WordArtUtil.getAngle(x,
					y);
		}

		this.reset();
	}
}