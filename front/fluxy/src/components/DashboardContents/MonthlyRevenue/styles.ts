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

export const MonthlyRevenueWrapper = styled.div.withConfig({
    shouldForwardProp: (prop) => prop !== "delay",
})<{ delay?: number }>`
    background: ${({ theme }) => theme["white-dark"]};
    border-radius: 16px;
    box-shadow: none;
    padding: 1.5rem 2rem;
    min-width: 21rem;
    min-height: 8rem;
    max-width: calc(100% / 3);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    position: relative;
    border-left: 4px solid ${(props) => props.theme["purple-400"]};
    
    opacity: 0;
    animation: ${fadeInUp} 0.6s ease-out forwards;
    animation-delay: ${(props) => props.delay || 0}ms;
`;

export const RevenueHeader = styled.div`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 0.3rem;
`;

export const RevenueTitle = styled.h3`
    font-size: 1rem;
    font-weight: 500;
    color: ${(props) => props.theme["brown-800"]};
    margin: 0;
    text-transform: none;
`;

export const RevenueAmount = styled.div`
    font-size: 2.2rem;
    font-weight: 700;
    color: ${({ theme }) => theme["purple-700"]};
    line-height: 1;
    margin-bottom: 0.2rem;
`;

export const ComparisonWrapper = styled.div.withConfig({
    shouldForwardProp: (prop) => prop !== "changeType",
})<{ changeType: "increase" | "decrease" | "same" }>`
    position: absolute;
    top: 1.2rem;
    right: 1.5rem;
    display: flex;
    align-items: center;
    gap: 0.3rem;
    padding: 0.2rem 0.7rem;
    border-radius: 8px;
    font-size: 0.95rem;
    font-weight: 500;
    background: ${({ theme, changeType }) => {
        switch (changeType) {
        case "increase":
            return theme["green-50"];
        case "decrease":
            return theme["red-50"];
        default:
            return theme["gray-50"];
        }
    }};
    border: 1.5px solid ${({ theme, changeType }) => {
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

export const ComparisonIcon = styled.div.withConfig({
    shouldForwardProp: (prop) => prop !== "changeType",
})<{ changeType: "increase" | "decrease" | "same" }>`
    display: flex;
    align-items: center;
    justify-content: center;
    color: ${({ theme, changeType }) => {
        switch (changeType) {
        case "increase":
            return theme["green-500"];
        case "decrease":
            return theme["red-500"];
        default:
            return theme["gray-400"];
        }
    }};
`;

export const ComparisonText = styled.span.withConfig({
    shouldForwardProp: (prop) => prop !== "changeType",
})<{ changeType: "increase" | "decrease" | "same" }>`
    font-size: 0.95rem;
    font-weight: 500;
    color: ${({ theme, changeType }) => {
        switch (changeType) {
        case "increase":
            return theme["green-500"];
        case "decrease":
            return theme["red-500"];
        default:
            return theme["gray-600"];
        }
    }};
`;

export const LoadingWrapper = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    height: 120px;
    color: ${({ theme }) => theme["gray-400"]};
`;
