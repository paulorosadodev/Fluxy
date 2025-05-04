// src/components/Loading.tsx
import React from "react";
import styled, { keyframes } from "styled-components";

const spin = keyframes`
    to {
        transform: rotate(360deg);
    }
`;

const LoaderWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    padding: 2rem;
`;

const Spinner = styled.div`
    width: 48px;
    height: 48px;
    border: 4px solid ${(props) => props.theme["purple-500"]};
    border-top: 4px solid transparent;
    border-radius: 50%;
    animation: ${spin} 1s linear infinite;
`;

const Loading: React.FC = () => (
    <LoaderWrapper>
        <Spinner />
    </LoaderWrapper>
);

export default Loading;