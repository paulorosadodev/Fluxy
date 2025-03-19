import { useState, useEffect } from "react";
import { AreaChart, Area, ResponsiveContainer } from "recharts";

const generateData = () => {
    return Array.from({ length: 7 }, (_, i) => ({
        name: `P${i + 1}`,
        value1: Math.random() * 100,
        value2: Math.random() * 100,
        value3: Math.random() * 100,
        value4: Math.random() * 100,
    }));
};

const AnimatedBackgroundChart = () => {
    const [data, setData] = useState(generateData());

    useEffect(() => {
        const interval = setInterval(() => {
            setData(generateData());
        }, 2000);

        return () => clearInterval(interval);
    }, []);

    return (
        <div style={{ width: "100%", height: "100%" }}>
            <ResponsiveContainer width="100%" height="100%">
                <AreaChart data={data} margin={{ top: 0, right: 0, left: 0, bottom: 0 }}>
                    {/* O mais ao fundo (maior opacidade) */}
                    <Area type="monotone" dataKey="value1" stroke="#EE6C4D" fill="rgba(238, 108, 77, 1)" strokeWidth={4} dot={false} isAnimationActive animationDuration={2000} />
                    <Area type="monotone" dataKey="value2" stroke="#F38D68" fill="rgba(243, 141, 104, 1)" strokeWidth={4} dot={false} isAnimationActive animationDuration={2000} />
                    <Area type="monotone" dataKey="value3" stroke="#662C91" fill="rgba(102, 44, 145, 1)" strokeWidth={4} dot={false} isAnimationActive animationDuration={2000} />
                    <Area type="monotone" dataKey="value4" stroke="#17a398" fill="rgba(23, 163, 152, 1)" strokeWidth={4} dot={false} isAnimationActive animationDuration={2000} />
                    {/* O mais na frente (menor opacidade) */}
                </AreaChart>
            </ResponsiveContainer>
        </div>
    );
};

export default AnimatedBackgroundChart;
