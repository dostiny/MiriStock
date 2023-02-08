const LossLabel = ({ viewBox, StockName, StockPrice }) => {
  const { cx, cy } = viewBox;

  let Price = StockPrice.toLocaleString();
  return (
    <svg
      className="recharts-text recharts-label"
      textAnchor="middle"
      dominantBaseline="central"
    >
      <text x={cx} y={cy} fill="#1E40AF">
        <tspan
          x={cx}
          dy="1.5em"
          alignmentBaseline="middle"
          fontSize="20"
          fontWeight={900}
        >
          {StockName}
        </tspan>

        <tspan x={cx} dy="1.5em" fontSize="14" fontWeight={600}>
          {Price + '원'}
        </tspan>
      </text>
    </svg>
  );
};

export default LossLabel;
