import { useEffect, useState } from "react";

type AnimatedNumberProps = {
    value: number;
    duration?: number;
    isMoney?: boolean;
};

export const AnimatedNumber = ({ value, duration = 800, isMoney = false }: AnimatedNumberProps) => {
    const [displayedValue, setDisplayedValue] = useState(0);

    useEffect(() => {
        const startTime = performance.now();

        const animate = (time: number) => {
            const elapsed = time - startTime;
            const progress = Math.min(elapsed / duration, 1);
            const current = progress * value;
            setDisplayedValue(current);

            if (progress < 1) requestAnimationFrame(animate);
        };

        requestAnimationFrame(animate);
    }, [value, duration]);

    const formatter = new Intl.NumberFormat("pt-BR", {
        style: isMoney ? "currency" : "decimal",
        currency: isMoney ? "BRL" : undefined,
        maximumFractionDigits: isMoney ? 2 : 0,
        minimumFractionDigits: isMoney ? 2 : 0,
    });

    return <>{formatter.format(displayedValue)}</>;
};
