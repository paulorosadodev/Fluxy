import { useState, useEffect } from "react";
import { LineChart, Line, ResponsiveContainer } from "recharts";

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
                <LineChart data={data} margin={{ top: 0, right: 0, left: 0, bottom: 0 }}>
                    <Line type="monotone" dataKey="value1" stroke="#EE6C4D" strokeWidth={8} dot={false} isAnimationActive animationDuration={2000} />
                    <Line type="monotone" dataKey="value2" stroke="#F38D68" strokeWidth={8} dot={false} isAnimationActive animationDuration={2000} />
                    <Line type="monotone" dataKey="value3" stroke="#662C91" strokeWidth={8} dot={false} isAnimationActive animationDuration={2000} />
                    <Line type="monotone" dataKey="value4" stroke="#17a398" strokeWidth={8} dot={false} isAnimationActive animationDuration={2000} />
                </LineChart>
            </ResponsiveContainer>
        </div>
    );
};

export default AnimatedBackgroundChart;
