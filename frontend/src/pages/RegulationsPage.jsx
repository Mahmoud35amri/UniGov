import React, { useState } from 'react';
import DashboardLayout from '../components/DashboardLayout';
import {
    Search,
    Download,
    Clock,
    Briefcase,
    FileCode,
    FileText,
    Calculator,
    ArrowRightCircle,
    RefreshCcw,
    GraduationCap,
    AlertTriangle,
    Award,
    BarChart2
} from 'lucide-react';

const RegulationsPage = () => {
    const [searchTerm, setSearchTerm] = useState('');

    const regulations = [
        {
            id: 1,
            category: 'Scolarité',
            title: 'Assiduité',
            content: 'Article 3 : L’assiduité à tous les enseignements est obligatoire. Le cumul des absences ne peut dépasser 10% du volume horaire global.',
            icon: Clock,
            updated: 'Sept 2025',
            pdfPath: '/documents/Guide_Eleve_Ingenieur_2021.pdf',
            color: 'text-blue-600 bg-blue-50'
        },
        {
            id: 2,
            category: 'Stages & PFE',
            title: 'Stages',
            content: 'Article 4 : Deux stages d’été obligatoires en 1ère et 2ème année. Chaque stage fait l’objet d’un rapport évalué par un jury.',
            icon: Briefcase,
            updated: 'Sept 2025',
            pdfPath: '/documents/Guide_Eleve_Ingenieur_2021.pdf',
            color: 'text-emerald-600 bg-emerald-50'
        },
        {
            id: 3,
            category: 'Stages & PFE',
            title: 'Projet de fin d’études',
            content: 'Article 5 : Un travail d’ingénierie encadré en 3ème année soutenu devant un jury. Évalue le caractère professionnel de la spécialité.',
            icon: FileCode,
            updated: 'Sept 2025',
            pdfPath: '/documents/Guide_Eleve_Ingenieur_2021.pdf',
            color: 'text-indigo-600 bg-indigo-50'
        },
        {
            id: 4,
            category: 'Examens & Notes',
            title: 'Modalités d’évaluation',
            content: 'Article 7 : Acquisition des connaissances évaluée par contrôle continu et examen final organisé en deux sessions.',
            icon: FileText,
            updated: 'Sept 2025',
            pdfPath: '/documents/Guide_Eleve_Ingenieur_2021.pdf',
            color: 'text-orange-600 bg-orange-50'
        },
        {
            id: 5,
            category: 'Examens & Notes',
            title: 'Calcul des moyennes',
            content: 'Article 8 : Moyenne résultant des notes obtenues dans les différentes épreuves. Pondération selon la forme des enseignements.',
            icon: Calculator,
            updated: 'Sept 2025',
            pdfPath: '/documents/Guide_Eleve_Ingenieur_2021.pdf',
            color: 'text-purple-600 bg-purple-50'
        },
        {
            id: 6,
            category: 'Scolarité',
            title: 'Passage',
            content: 'Article 9 : Admis avec une moyenne générale ≥ 10/20 et une moyenne ≥ 08/20 dans chacune des unités d’enseignement.',
            icon: ArrowRightCircle,
            updated: 'Sept 2025',
            pdfPath: '/documents/Guide_Eleve_Ingenieur_2021.pdf',
            color: 'text-cyan-600 bg-cyan-50'
        },
        {
            id: 7,
            category: 'Examens & Notes',
            title: 'Rattrapage',
            content: 'Article 10 : Autorisé si non admis à la session principale. Matières limitées à celles avec moyenne < 10/20.',
            icon: RefreshCcw,
            updated: 'Sept 2025',
            pdfPath: '/documents/Guide_Eleve_Ingenieur_2021.pdf',
            color: 'text-red-600 bg-red-50'
        },
        {
            id: 8,
            category: 'Examens & Notes',
            title: 'Crédits',
            content: 'Article 11 : Admis par crédit si moyenne générale ≥ 10/20 et moyenne ≥ 08/20 dans au moins 4 UE de l’année considérée.',
            icon: GraduationCap,
            updated: 'Sept 2025',
            pdfPath: '/documents/Guide_Eleve_Ingenieur_2021.pdf',
            color: 'text-amber-600 bg-amber-50'
        },
        {
            id: 9,
            category: 'Scolarité',
            title: 'Redoublement',
            content: 'Article 12 : Autorisé une seule fois au cours de la scolarité. Bénéfice gardé des matières avec moyenne ≥ 10/20.',
            icon: AlertTriangle,
            updated: 'Sept 2025',
            pdfPath: '/documents/Guide_Eleve_Ingenieur_2021.pdf',
            color: 'text-slate-600 bg-slate-50'
        },
        {
            id: 10,
            category: 'Diplôme',
            title: 'Obtention du diplôme',
            content: 'Article 13 : Délivré après avoir satisfait aux examens, validé les stages, les crédits et obtenu ≥ 10/20 au PFE.',
            icon: Award,
            updated: 'Sept 2025',
            pdfPath: '/documents/Guide_Eleve_Ingenieur_2021.pdf',
            color: 'text-rose-600 bg-rose-50'
        },
        {
            id: 11,
            category: 'Diplôme',
            title: 'Classement',
            content: 'Article 15 : Basé sur les moyennes des sessions principales (60%), le PFE (22%), les stages (16%) et le parascolaire (2%).',
            icon: BarChart2,
            updated: 'Sept 2025',
            pdfPath: '/documents/Guide_Eleve_Ingenieur_2021.pdf',
            color: 'text-sky-600 bg-sky-50'
        }
    ];

    const filtered = regulations.filter(r =>
        r.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        r.category.toLowerCase().includes(searchTerm.toLowerCase()) ||
        r.content.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <DashboardLayout title="Règlements Intérieurs">
            <div className="max-w-6xl mx-auto space-y-8">
                <div className="bg-primary-600 rounded-3xl p-10 text-white relative overflow-hidden shadow-xl">
                    <div className="relative z-10 flex flex-col md:flex-row md:items-center justify-between gap-8">
                        <div className="max-w-xl">
                            <h1 className="text-3xl font-bold mb-3">Directives Académiques</h1>
                            <p className="text-primary-100 font-medium">Consultez les règlements officiels de l'université, les politiques et les documents relatifs aux droits des étudiants.</p>
                        </div>
                        <div className="relative max-w-sm w-full">
                            <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-primary-300" size={20} />
                            <input
                                type="text"
                                placeholder="Rechercher..."
                                className="w-full bg-primary-700/50 border border-primary-500 rounded-2xl py-3 pl-12 pr-4 text-white placeholder:text-primary-300 focus:outline-none focus:ring-2 focus:ring-primary-400 transition-all font-medium"
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                            />
                        </div>
                    </div>
                    <div className="absolute top-0 right-0 -translate-y-1/2 translate-x-1/2 w-64 h-64 bg-primary-500 rounded-full blur-3xl opacity-50"></div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {filtered.map((reg) => (
                        <div key={reg.id} className="card group hover:shadow-md transition-all flex flex-col">
                            <div className="flex items-start justify-between mb-4">
                                <div className={`p-3 rounded-2xl ${reg.color} bg-opacity-10 dark:bg-opacity-20`}>
                                    <reg.icon size={22} />
                                </div>
                                <span className="bg-slate-100 dark:bg-slate-800 text-slate-500 dark:text-slate-400 px-3 py-1 rounded-full text-[9px] font-bold uppercase tracking-wider">
                                    {reg.category}
                                </span>
                            </div>
                            <h3 className="text-lg font-bold text-slate-800 dark:text-slate-100 mb-2 group-hover:text-primary-600 transition-colors tracking-tight">{reg.title}</h3>
                            <p className="text-slate-500 dark:text-slate-400 text-xs leading-relaxed mb-6 flex-1">{reg.content}</p>

                            <div className="pt-4 border-t border-slate-50 dark:border-slate-800 flex items-center justify-between mt-auto">
                                <span className="text-[10px] font-bold text-slate-400 dark:text-slate-500 uppercase tracking-tight">Guide 2025</span>
                                <a
                                    href={reg.pdfPath}
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className="flex items-center gap-2 bg-primary-50 dark:bg-primary-900/30 text-primary-600 dark:text-primary-400 px-4 py-2 rounded-xl font-bold text-[10px] hover:bg-primary-600 hover:text-white dark:hover:bg-primary-600 transition-all"
                                >
                                    <Download size={14} />
                                    <span>LIRE (§)</span>
                                </a>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </DashboardLayout>
    );
};

export default RegulationsPage;
