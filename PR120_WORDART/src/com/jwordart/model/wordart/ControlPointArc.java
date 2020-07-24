package com.jwordart.model.wordart;

/**
 * Ÿ�� ��ũ�� ���� �����̴� ��Ʈ�� ����Ʈ�̴�.
 */
public class ControlPointArc extends ControlPoint {
	private double currentAngle = 0; // ���� ����
	private double currentRadiusRatio = 0; // ���� �ݰ� ����(�ܰ� Ÿ�� �ݰ� ����)
	private double minRadiusRatio = 0; // �ּ� �ݰ� ����(�ܰ� Ÿ�� �ݰ� ����

	/**
	 * ������
	 *
	 * @param wa
	 *            ���� ��Ʈ currentRadiusRatio ���� �ݰ� ���� minRadiusRatio �ּ� �ݰ� ����
	 *            currentAngle ���� ����
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
	 * ���� �ݰ� ������ ���� ������ ���߾� ��Ʈ�� ����Ʈ�� ��ǥ�� �� �����Ѵ�. ���� ��Ʈ�� ũ�Ⱑ �ٲ�� �ڵ����� ��Ʈ�� ����Ʈ��
	 * ��ǥ�� ���� ������ ���� �Ǿ�����.
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
	 * ���� ������ �����Ѵ�.
	 */
	public double getCurrentAngle() {
		return this.currentAngle;
	}

	/**
	 * ���� �ݰ� ������ �����Ѵ�.
	 */
	public double getCurrentRadiusRatio() {
		return this.currentRadiusRatio;
	}

	/**
	 * �ּ� �ݰ� ������ �����Ѵ�.
	 */
	public double getMinRadiusRatio() {
		return this.minRadiusRatio;
	}

	/**
	 * �� Ŭ������ Ŭ��(Clone) �޼ҵ��̴�.
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
	 * ��Ʈ�� ����Ʈ�� �߾����� ���� ��ǥ�� �����Ѵ�.
	 */
	@Override
	public double getCenterX() {
		return super.getX() + ControlPoint.defaultWidth / 2.0;
	}

	/**
	 * ��Ʈ�� ����Ʈ�� �߾� ���� ��ǥ�� �����Ѵ�.
	 */
	@Override
	public double getCenterY() {
		return super.getY() + ControlPoint.defaultHeight / 2.0;
	}

	/**
	 * ��Ʈ�� ����Ʈ�� �̵��Ѵ�.
	 *
	 * @param dx
	 *            ���� �̵� ���� dy ���� �̵� ����
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