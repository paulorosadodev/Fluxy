type AnimatedNumberProps = {
    value: number;
    isMoney?: boolean;
};

export const AnimatedNumber = ({ value, isMoney = false }: AnimatedNumberProps) => {
    const formatter = new Intl.NumberFormat("pt-BR", {
        style: isMoney ? "currency" : "decimal",
        currency: isMoney ? "BRL" : undefined,
        maximumFractionDigits: isMoney ? 2 : 0,
        minimumFractionDigits: isMoney ? 2 : 0,
    });

    return <>{formatter.format(value)}</>;
};
