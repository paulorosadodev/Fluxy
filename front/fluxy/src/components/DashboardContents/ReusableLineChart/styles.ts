import styled, { keyframes } from "styled-components";
import { ResponsiveContainer } from "recharts";

export const ChartWrapper = styled.div`
    width: 100%;
    height: 23rem;
    background: ${({ theme }) => theme["white-dark"]};
    padding: 2rem 2.5rem 2rem 2.5rem;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: flex-start;
    position: relative;
    z-index: 0;
`;

export const Title = styled.h3`
    font-size: 1.25rem;
    font-weight: 700;
    color: ${({ theme }) => theme["gray-900"] || "#222"};
`;

export const HeaderContainer = styled.div`
    display: flex;
    align-items: center;
    width: 100%;
    justify-content: space-between;
`;

export const SelectContainer = styled.div`
    min-width: 220px;
    display: flex;
    align-items: center;
    gap: 8px;
`;

export const SelectDropdown = styled.select`
    width: 220px;
    min-width: 220px;
    max-width: 220px;
    padding: 8px 12px;
    border-radius: 6px;
    border: 1px solid ${({ theme }) => theme["gray-300"] || "#e1e1e6"};
    background: ${({ theme }) => theme["gray-200"]};
    font-size: 0.875rem;
    cursor: pointer;
    
    &:focus {
        outline: none;
        border-color: ${({ theme }) => theme["primary"] || "#8884d8"};
    }
`;

export const ContainerWithSpinner = styled.div<{ $loading?: boolean }>`
    position: relative;
    
    ${props => props.$loading && `
        & > div > div {
            opacity: 0.6;
            transition: opacity 0.2s;
        }
    `}
`;

export const SpinAnimation = keyframes`
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
`;

export const LoadingOverlay = styled.div`
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 10;
    background: rgba(255,255,255,0.8);
    padding: 16px;
    border-radius: 8px;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8px;
`;

export const Spinner = styled.div`
    width: 30px;
    height: 30px;
    border: 4px solid rgba(0, 0, 0, 0.1);
    border-left-color: #8884d8;
    border-radius: 50%;
    animation: ${SpinAnimation} 1s linear infinite;
`;

export const StyledResponsiveContainer = styled(ResponsiveContainer)<{ $loading?: boolean }>`
    opacity: ${props => props.$loading ? 0.6 : 1};
`;
