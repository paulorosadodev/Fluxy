import { useEffect, useState } from "react";

type AnimatedNumberProps = {
    value: number;
    duration?: number;
};

export const AnimatedNumber = ({ value, duration = 800 }: AnimatedNumberProps) => {
    const [displayedValue, setDisplayedValue] = useState(0);

    useEffect(() => {
        const startTime = performance.now();

        const animate = (time: number) => {
            const elapsed = time - startTime;
            const progress = Math.min(elapsed / duration, 1);
            const current = Math.floor(progress * value);
            setDisplayedValue(current);

            if (progress < 1) requestAnimationFrame(animate);
        };

        requestAnimationFrame(animate);
    }, [value, duration]);

    const formatter = new Intl.NumberFormat("pt-BR");

    return <>{formatter.format(displayedValue)}</>;
};
