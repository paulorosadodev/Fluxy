import { Address, Employee, LegalEntityCustomer, NaturalPersonCustomer, PaymentMethod, Product, Supplier } from "../@types";

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

export function formatPhoneNumber(phone: string[]) {

    const cleaned = phone[0].replace(/\D/g, "");

    if (cleaned.length !== 11) return phone; 

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
    return `${address.street}, ${address.number} |  ${address.city} - ${address.neighborhood} | ${formatCEP(address.cep)} `;
}

export function formatSupplier(supplier: Supplier) {
    return `${supplier.name} | ${formatCNPJ(supplier.cnpj)}`;
}

export function formatProduct(product: Product) {
    return product.name;
}

export function formatCustomer(customer: NaturalPersonCustomer | LegalEntityCustomer) {
    if ("cpf" in customer) {
        return `${customer.name} | ${formatCPF(customer.cpf)}`;
    }
    return `${customer.legalName} | ${formatCNPJ(customer.cnpj)}`;
}

export function formatEmployee(employee: Employee[]) {
    return `${employee[0].name} | ${employee[0].employeeNumber}`;
}

export function formatPaymentMethod(paymentMethod: PaymentMethod) {
    return `${paymentMethod.type} - ${paymentMethod.installments}x`;
}

export function formatStateRegistration(ie: string) {

    const cleaned = ie.replace(/\D/g, "");

    if (cleaned.length !== 9) return ie;

    return cleaned.replace(/(\d{7})(\d{2})/, "$1-$2");
}

export function formatDate(date: string | Date) {

    const d = new Date(date);

    if (isNaN(d.getTime())) return String(date);

    const day = String(d.getDate()).padStart(2, "0");
    const month = String(d.getMonth() + 1).padStart(2, "0");
    const year = d.getFullYear();
    const hours = String(d.getHours()).padStart(2, "0");
    const minutes = String(d.getMinutes()).padStart(2, "0");
    const hasTime = d.getHours() !== 0 || d.getMinutes() !== 0 || d.getSeconds() !== 0;

    return hasTime ? `${hours}:${minutes} - ${day}/${month}/${year}` : `${day}/${month}/${year}`;
}
