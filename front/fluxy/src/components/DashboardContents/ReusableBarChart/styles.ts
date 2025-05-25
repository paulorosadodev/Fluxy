import styled, { keyframes } from "styled-components";

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
    margin-bottom: 1rem;
`;

const spin = keyframes`
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
`;

export const Spinner = styled.div`
    border: 4px solid ${({ theme }) => theme["gray-300"] || "#f3f3f3"};
    border-top: 4px solid ${({ theme }) => theme["primary"] || "#8884d8"};
    border-radius: 50%;
    width: 40px;
    height: 40px;
    animation: ${spin} 2s linear infinite;
`;

export const LoadingOverlay = styled.div`
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(255, 255, 255, 0.8);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    z-index: 10;
    gap: 1rem;
    
    span {
        font-size: 0.875rem;
        color: ${({ theme }) => theme["gray-700"] || "#666"};
        font-weight: 500;
    }
`;

export const ContainerWithSpinner = styled.div<{ $loading: boolean }>`
    position: relative;
    width: 100%;
    opacity: ${({ $loading }) => ($loading ? 0.7 : 1)};
    transition: opacity 0.3s ease;
`;
