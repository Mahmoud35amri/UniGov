import React, { useState, useEffect } from 'react';
import DashboardLayout from '../components/DashboardLayout';
import api from '../services/api';
import { useAuth } from '../context/AuthContext';
import {
    Gavel,
    Search,
    Filter,
    Calendar,
    ExternalLink,
    CheckCircle2,
    FileText,
    Trash2
} from 'lucide-react';

const DecisionsPage = () => {
    const [decisions, setDecisions] = useState([]);
    const [loading, setLoading] = useState(true);
    const { user } = useAuth();

    useEffect(() => {
        fetchDecisions();
    }, []);

    const fetchDecisions = async () => {
        try {
            const res = await api.getAllDecisions();
            setDecisions(res.data);
        } catch (err) {
            console.error('Failed to fetch decisions:', err);
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm('Voulez-vous vraiment supprimer cette décision officielle ?')) return;
        try {
            await api.deleteDecision(id);
            fetchDecisions();
        } catch (err) {
            console.error('Delete failed:', err);
            alert('Échec de la suppression.');
        }
    };

    const getCategoryStyles = (cat) => {
        switch (cat) {
            case 'Académique': return 'bg-blue-50 text-blue-600 border-blue-100 dark:bg-blue-500/10 dark:text-blue-400 dark:border-blue-500/20';
            case 'Discipline': return 'bg-red-50 text-red-600 border-red-100 dark:bg-red-500/10 dark:text-red-400 dark:border-red-500/20';
            case 'Infrastructure': return 'bg-amber-50 text-amber-600 border-amber-100 dark:bg-amber-500/10 dark:text-amber-400 dark:border-amber-500/20';
            default: return 'bg-slate-50 text-slate-600 border-slate-100 dark:bg-slate-500/10 dark:text-slate-400 dark:border-slate-500/20';
        }
    };

    return (
        <DashboardLayout title="Suivi des Décisions Officielles">
            <div className="flex flex-col gap-8 max-w-6xl mx-auto">
                <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
                    <p className="text-slate-500 dark:text-slate-400 font-medium">
                        Consultez les dernières décisions administratives et académiques de l'ENICarthage.
                    </p>
                    <div className="flex items-center gap-3">
                        <div className="relative">
                            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" size={18} />
                            <input
                                type="text"
                                placeholder="Rechercher une décision..."
                                className="input pl-10 bg-white dark:bg-[#1a1c2e] min-w-[300px]"
                            />
                        </div>
                        <button className="flex items-center gap-2 px-4 py-2 bg-white dark:bg-[#1a1c2e] border border-slate-200 dark:border-slate-800 rounded-xl text-slate-600 dark:text-slate-400 font-bold hover:bg-slate-50 dark:hover:bg-slate-800 transition-all">
                            <Filter size={18} />
                            <span>Filtrer</span>
                        </button>
                    </div>
                </div>

                <div className="grid gap-6">
                    {loading ? (
                        <div className="py-20 flex justify-center"><div className="animate-spin rounded-full h-10 w-10 border-b-2 border-primary-600"></div></div>
                    ) : decisions.length === 0 ? (
                        <div className="card p-20 text-center border-dashed border-slate-200 dark:border-slate-800">
                            <div className="w-16 h-16 bg-slate-50 dark:bg-slate-800 text-slate-300 dark:text-slate-600 rounded-full flex items-center justify-center mx-auto mb-4">
                                <Gavel size={32} />
                            </div>
                            <p className="text-slate-400 dark:text-slate-500 font-medium font-black uppercase text-xs tracking-widest">Aucune décision officielle</p>
                        </div>
                    ) : (
                        decisions.map((decision) => (
                            <div key={decision.id} className="group bg-white dark:bg-[#1a1c2e] rounded-[2rem] p-8 border border-slate-100 dark:border-slate-800 hover:shadow-2xl hover:shadow-primary-500/5 transition-all relative overflow-hidden">
                                <div className="absolute top-0 right-0 w-32 h-32 bg-primary-500/5 blur-3xl -mr-16 -mt-16 rounded-full group-hover:bg-primary-500/10 transition-colors" />

                                <div className="flex flex-col md:flex-row gap-8 relative items-start">
                                    <div className="shrink-0 w-20 flex flex-col items-center justify-center bg-slate-50 dark:bg-slate-800/50 rounded-2xl py-4 border border-slate-100 dark:border-slate-700/50">
                                        <span className="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-1">
                                            {new Date(decision.decisionDate).toLocaleDateString('fr-FR', { month: 'short' })}
                                        </span>
                                        <span className="text-2xl font-black text-slate-800 dark:text-white leading-none">
                                            {new Date(decision.decisionDate).getDate()}
                                        </span>
                                        <span className="text-[10px] font-black text-slate-400 mt-1">
                                            {new Date(decision.decisionDate).getFullYear()}
                                        </span>
                                    </div>

                                    <div className="flex-1 space-y-4">
                                        <div className="flex flex-wrap items-center gap-3">
                                            <span className={`px-4 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest border transition-colors ${getCategoryStyles(decision.category)}`}>
                                                {decision.category || 'Général'}
                                            </span>
                                            <span className="px-4 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest bg-emerald-50 text-emerald-600 border border-emerald-100 dark:bg-emerald-500/10 dark:text-emerald-400 dark:border-emerald-500/20 flex items-center gap-1.5">
                                                <CheckCircle2 size={12} />
                                                Officiel
                                            </span>
                                        </div>

                                        <h3 className="text-xl md:text-2xl font-black text-slate-800 dark:text-white group-hover:text-primary-600 transition-colors leading-tight">
                                            {decision.title}
                                        </h3>

                                        <p className="text-slate-500 dark:text-slate-400 font-medium leading-relaxed max-w-4xl">
                                            {decision.content}
                                        </p>

                                        <div className="flex items-center gap-6 pt-4 border-t border-slate-50 dark:border-slate-800/50">
                                            <div className="flex items-center gap-2 text-xs font-bold text-slate-400 uppercase tracking-tight">
                                                <Calendar size={14} className="text-primary-500" />
                                                Publié le {new Date(decision.decisionDate).toLocaleDateString()}
                                            </div>
                                            <button className="flex items-center gap-2 text-xs font-black text-primary-600 hover:text-primary-700 transition-colors uppercase tracking-widest">
                                                <FileText size={14} />
                                                Voir le document
                                                <ExternalLink size={14} className="opacity-50" />
                                            </button>
                                            {user.role === 'ROLE_DELEGUE' && (
                                                <button
                                                    onClick={() => handleDelete(decision.id)}
                                                    className="ml-auto p-2 text-red-500 hover:bg-red-50 dark:hover:bg-red-500/10 rounded-xl transition-all"
                                                >
                                                    <Trash2 size={20} />
                                                </button>
                                            )}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        ))
                    )}
                </div>
            </div>
        </DashboardLayout>
    );
};

export default DecisionsPage;
