package com.bank.test.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import com.bank.test.beans.BankDetailsBean;
import com.bank.test.daos.BankDetails;
import com.bank.test.utils.HibernateUtil;

public class BankDetailsManager {
	public final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public BankDetailsBean getBankDetailsByIfsc(String ifsc) {
		BankDetailsBean detailsBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(BankDetails.class);
			criteria.add(Restrictions.eq("bankIfsc", ifsc));
			List<BankDetails> detailsList = criteria.list();
			System.out.println("details list size: " + detailsList.size());
			if(detailsList.size()==1) {
				detailsBean = new BankDetailsBean();
				for(BankDetails detail: detailsList) {
					detailsBean.setBankId(detail.getBankId());
					detailsBean.setBankName(detail.getBankName());
					detailsBean.setBankIfsc(detail.getBankIfsc());
					detailsBean.setBankBranch(detail.getBankBranch());
					detailsBean.setBankAddress(detail.getBankAddress());
					detailsBean.setBankCity(detail.getBankCity());
					detailsBean.setBankDistrict(detail.getBankDistrict());
					detailsBean.setBankState(detail.getBankState());
				}
			}
			transaction.commit();
		} catch(RuntimeException re) {
			if(transaction!=null) {
				transaction.rollback();
			}
			System.out.println(re);
			throw re;
		} finally {
			if(session!=null && session.isOpen()) {
				session.close();
			}
		}
		return detailsBean;
	}
	
	public List<BankDetailsBean> getBankDetailsByNameCity(String bankName, String bankCity){
		List<BankDetailsBean> detailsBeanList = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(BankDetails.class);
			Criterion nameCriterion = Restrictions.eq("bankName", bankName);
			Criterion cityCriterion = Restrictions.eq("bankCity", bankCity);
			LogicalExpression logExp = Restrictions.and(nameCriterion, cityCriterion);
			criteria.add(logExp);
			List<BankDetails> detailsList = criteria.list();
			System.out.println("details list size: " + detailsList.size());
			if(detailsList.size()>0) {
				detailsBeanList = new ArrayList<BankDetailsBean>();
				for(BankDetails detail: detailsList) {
					BankDetailsBean detailsBean = new BankDetailsBean();
					detailsBean.setBankId(detail.getBankId());
					detailsBean.setBankName(detail.getBankName());
					detailsBean.setBankIfsc(detail.getBankIfsc());
					detailsBean.setBankBranch(detail.getBankBranch());
					detailsBean.setBankAddress(detail.getBankAddress());
					detailsBean.setBankCity(detail.getBankCity());
					detailsBean.setBankDistrict(detail.getBankDistrict());
					detailsBean.setBankState(detail.getBankState());
					detailsBeanList.add(detailsBean);
					detailsBean = null;
				}
			}
			transaction.commit();
		} catch(RuntimeException re) {
			if(transaction!=null) {
				transaction.rollback();
			}
			System.out.println(re);
			throw re;
		} finally {
			if(session!=null && session.isOpen()) {
				session.close();
			}
		}
		return detailsBeanList;
	}
}
