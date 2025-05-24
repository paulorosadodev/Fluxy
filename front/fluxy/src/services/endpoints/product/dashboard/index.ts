import { api } from "../../../api";

export const fetchProductsTotalStock = async () => {

    try {
        const response = await api.get("/products/total-stock");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de estoque");
    }

};

export const fetchProductsCount = async () => {

    try {
        const response = await api.get("/products/total-products");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de produtos");
    }

};

export const fetchCategoriesCount = async () => {

    try {
        const response = await api.get("/categories/total-categories");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de categorias");
    }

};

export const fetchAveragePrice = async () => {

    try {
        const response = await api.get("/products/average-price");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar preço médio de produto");
    }

};

export const fetchTotalPrice = async () => {

    try {
        const response = await api.get("/products/total-price");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar preço total do estoque");
    }

};

export const fetchProductsCountByCategory = async () => {

    try {
        const response = await api.get("/products/products-count-by-category");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar quantidade de produtos por categoria");
    }

};

export const fetchMostExpensiveProducts = async () => {

    try {
        const response = await api.get("/products/most-expensive-products");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar produtos mais caros");
    }

};

export const fetchLeastExpensiveProducts = async () => {

    try {
        const response = await api.get("/products/least-expensive-products");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar produtos mais baratos");
    }

};

export const fetchLowStockProducts = async () => {
    try {
        const response = await api.get("/products/low-stock-products");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar produtos com baixo estoque");
    }
};

export const fetchProductPriceHistory = async (productId: string) => {

    const requestBody = {
        productId: Number(productId),
    };

    try {
        const response = await api.post("/products/price-history", requestBody);

        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar histórico de preço");
    }
};



