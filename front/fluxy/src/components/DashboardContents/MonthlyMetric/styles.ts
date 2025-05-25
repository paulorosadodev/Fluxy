import styled, { keyframes } from "styled-components";

const fadeInUp = keyframes`
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
`;

export const MonthlyMetricWrapper = styled.div.withConfig({
    shouldForwardProp: (prop) => prop !== "delay" && prop !== "color",
})<{ delay: number; color?: string }>`
    padding: 24px;
    background: ${({ theme }) => theme["white-dark"]};
    border-radius: 12px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    border: 1px solid ${({ theme }) => theme["gray-200"]};
    display: flex;
    flex-direction: column;
    min-height: 120px;
    position: relative;
    animation: ${fadeInUp} 0.6s ease-out;
    animation-delay: ${props => props.delay}ms;
    animation-fill-mode: both;
    
    &::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        height: 4px;
        background: ${props => props.color || props.theme["green-500"]};
        border-radius: 12px 12px 0 0;
    }
`;

export const MetricHeader = styled.div`
    display: flex;
    flex-direction: column;
    gap: 8px;
    margin-top: auto;
`;

export const MetricTitle = styled.h3`
    font-size: 16px;
    font-weight: 600;
    color: ${({ theme }) => theme["brown-800"]};
    margin: 0;
    text-transform: capitalize;
`;

export const MetricAmount = styled.div`
    font-size: 28px;
    font-weight: 700;
    color: ${({ theme }) => theme["purple-900"]};
    margin: 0;
`;

export const ComparisonWrapper = styled.div.withConfig({
    shouldForwardProp: (prop) => prop !== "changeType",
})<{ changeType: "increase" | "decrease" | "same" }>`
    display: flex;
    align-items: center;
    gap: 6px;
    margin-bottom: 16px;
    align-self: flex-end;
`;

export const ComparisonIcon = styled.div.withConfig({
    shouldForwardProp: (prop) => prop !== "changeType",
})<{ changeType: "increase" | "decrease" | "same" }>`
    color: ${({ theme, changeType }) => {
        switch (changeType) {
        case "increase":
            return theme["green-500"];
        case "decrease":
            return theme["red-500"];
        default:
            return theme["gray-200"];
        }
    }};
    display: flex;
    align-items: center;
`;

export const ComparisonText = styled.span.withConfig({
    shouldForwardProp: (prop) => prop !== "changeType",
})<{ changeType: "increase" | "decrease" | "same" }>`
    font-size: 14px;
    font-weight: 600;
    color: ${({ theme, changeType }) => {
        switch (changeType) {
        case "increase":
            return theme["green-500"];
        case "decrease":
            return theme["red-500"];
        default:
            return theme["gray-200"];
        }
    }};
`;

export const LoadingWrapper = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 120px;
    color: ${({ theme }) => theme["gray-200"]};
    font-size: 14px;
`;
