import { Address, Customer, Employee, PaymentMethod, Product, Supplier } from "../@types";

const SECRET_KEY = import.meta.env.VITE_SECRET_KEY;

export function encryptRole(role: string): string {
    const combined = `${SECRET_KEY}:${role}`;
    return btoa(unescape(encodeURIComponent(combined))); 
}

export function decryptRole(encrypted: string): string {
    try {
        const decoded = decodeURIComponent(escape(atob(encrypted)));
        const [key, role] = decoded.split(":");
        if (key !== SECRET_KEY) throw new Error("Chave inválida");
        return role;
    } catch {
        return "";
    }
}

export function removeNonDigits(value: string)  {
    return value.replace(/\D/g, "");
}

export function isValidPhoneNumber(phone: string): boolean {
    const cleanedPhone = phone.replace(/\D/g, ""); 
    const phoneRegex = /^[1-9]{2}9[0-9]{8}$/;
    return phoneRegex.test(cleanedPhone) && !isValidCEP(cleanedPhone);
}

export function isValidStateRegistration(ie: string): boolean {
    const cleaned = ie.replace(/\D/g, "");

    if (!/^\d{9}$/.test(cleaned)) {
        return false;
    }

    if (/^(\d)\1+$/.test(cleaned)) {
        return false;
    }

    return true;
}

export function isValidCNPJ(cnpj: string): boolean {
    cnpj = cnpj.replace(/[^\d]+/g, "");

    if (cnpj.length !== 14 || /^(\d)\1+$/.test(cnpj)) return false;

    const calcCheckDigit = (base: string, weights: number[]) => {
        let sum = 0;
        for (let i = 0; i < weights.length; i++) {
            sum += parseInt(base.charAt(i)) * weights[i];
        }
        const rest = sum % 11;
        return rest < 2 ? 0 : 11 - rest;
    };

    const base = cnpj.slice(0, 12);
    const firstDigit = calcCheckDigit(base, [5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2]);
    const secondDigit = calcCheckDigit(base + firstDigit, [6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2]);

    return cnpj === base + firstDigit.toString() + secondDigit.toString();
}


export function isValidCPF(cpf: string): boolean {
    cpf = cpf.replace(/[^\d]+/g, "");

    if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false;

    let sum = 0;
    for (let i = 0; i < 9; i++) {
        sum += parseInt(cpf.charAt(i)) * (10 - i);
    }

    let rev = 11 - (sum % 11);
    if (rev === 10 || rev === 11) rev = 0;
    if (rev !== parseInt(cpf.charAt(9))) return false;

    sum = 0;
    for (let i = 0; i < 10; i++) {
        sum += parseInt(cpf.charAt(i)) * (11 - i);
    }

    rev = 11 - (sum % 11);
    if (rev === 10 || rev === 11) rev = 0;
    if (rev !== parseInt(cpf.charAt(10))) return false;

    return true;
}

export function cleanCPF(cpf: string): string {
    return cpf.replace(/\D/g, ""); 
}

export function cleanCNPJ(cnpj: string): string {
    return cnpj.replace(/\D/g, ""); 
}


export function isValidCEP(cep: string): boolean {
    cep = cep.replace(/[^\d]+/g, "");

    return /^\d{8}$/.test(cep);
}

export function formatMoney(value: number) {
    return `R$ ${Number(value).toFixed(2).replace(".", ",")}`;
    
}

export function formatStock(value: number) {
    return `${value} und`;
}

export function formatCPF(cpf: string) {
    const cleaned = cpf.replace(/\D/g, "");

    if (cleaned.length !== 11) return cpf; 

    return cleaned.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
}

export function formatCNPJ(cnpj: string) {

    const cleaned = cnpj.replace(/\D/g, "");

    if (cleaned.length !== 14) return cnpj; 

    return cleaned.replace(
        /(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/,
        "$1.$2.$3/$4-$5"
    );
}

export function formatPhoneNumber(phone: unknown): string {

    if (String(phone).includes(",")) {
        return String(phone)
            .split(",") 
            .map((num) => formatPhoneNumber(num.trim())) 
            .join(", "); 
    }

    const cleaned = String(phone).replace(/\D/g, "");

    if (cleaned.length !== 11) return String(phone); 

    return cleaned.replace(
        /(\d{2})(\d{1})(\d{4})(\d{4})/,
        "($1) $2 $3-$4"
    );
}

export function formatCEP(cep: string) {

    const cleaned = cep.replace(/\D/g, "");

    if (cleaned.length !== 8) return cep;

    return cleaned.replace(/(\d{5})(\d{3})/, "$1-$2");
}

export function formatAddress(address: Address) {
    return `${address.street}, ${address.number} `;
}

export function formatSupplier(supplier: Supplier) {
    return `${supplier.name} | ${formatCNPJ(supplier.cnpj)}`;
}

export function formatProduct(product: Product) {
    return product.name;
}

export function formatCustomer(customer: Customer) {
    if ("cpf" in customer) {
        return `${customer.name} | ${formatCPF(customer.cpf ?? "")}`;
    }
    return `${customer.legalName} | ${formatCNPJ(customer.cnpj ?? "")}`;
}

export function formatPurchaseProduct(product: Product) {
    return `${product.id} | ${product.name}`;
}

export function formatEmployee(employee: Employee) {
    return `${employee.name} | ${employee.employeeNumber}`;
}

export function formatPaymentMethod(paymentMethod: PaymentMethod) {
    return `${paymentMethod.type} - ${paymentMethod.installments}x`;
}

export function formatStateRegistration(ie: string) {

    const cleaned = ie.replace(/\D/g, "");

    if (cleaned.length !== 9) return ie;

    return cleaned.replace(/(\d{7})(\d{2})/, "$1-$2");
}

export function formatDate(date: string): string {

    const cleanDate = date.replace("undefined", "");

    const [fullDate, time] = cleanDate.split("T"); 
    const [year, month, day] = fullDate.split("-");

    const formattedDate = `${day}/${month}/${year}`;

    if (time) {
        const [hour, minute] = time.split(":");
        return `${hour}:${minute} - ${formattedDate}`;
    }

    return formattedDate;
}
